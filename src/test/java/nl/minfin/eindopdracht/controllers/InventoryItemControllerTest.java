package nl.minfin.eindopdracht.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.minfin.eindopdracht.dto.AuthDto;
import nl.minfin.eindopdracht.dto.InventoryItemAddedDto;
import nl.minfin.eindopdracht.dto.InventoryItemDto;
import nl.minfin.eindopdracht.objects.enums.InventoryType;
import nl.minfin.eindopdracht.objects.models.InventoryItem;
import nl.minfin.eindopdracht.repositories.InventoryItemRepository;
import nl.minfin.eindopdracht.services.InventoryItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InventoryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryItemService inventoryItemService;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    MvcResult result;
    String token;

    InventoryItem inv1;
    InventoryItem inv2;
    InventoryItemDto inventoryItemDto;
    InventoryItemAddedDto inventoryItemAddedDto;

    @BeforeEach
    void setUp() throws Exception {

        inv1 = new InventoryItem("Voorbumper vervangen", 40.00, InventoryType.MANUAL_ACTION);
        inv2 = new InventoryItem("Voorbumper", 230.00, InventoryType.CAR_PART);

        inventoryItemRepository.save(inv1);
        inventoryItemRepository.save(inv2);

        AuthDto authDto = new AuthDto();
        authDto.username = "backoffice";
        authDto.password = "backoffice";

        result = mockMvc.perform(
                post("/auth").contentType(APPLICATION_JSON_UTF8).content(asJsonString(authDto))
        ).andReturn();

        token = result.getResponse().getHeader("Authorization");
    }

    @Test
    void getAllInventoryItems() throws Exception {
        mockMvc.perform(get("/api/inventory").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].inventoryItemId").value(inv2.getInventoryItemId().toString()))
                .andExpect(jsonPath("$[0].name").value(inv2.getName()))
                .andExpect(jsonPath("$[0].cost").value(inv2.getCost()))
                .andExpect(jsonPath("$[0].stock").value(inv2.getStock()))
                .andExpect(jsonPath("$[0].inventoryType").value(inv2.getInventoryType().toString()))
                .andExpect(jsonPath("$[1].inventoryItemId").value(inv1.getInventoryItemId().toString()))
                .andExpect(jsonPath("$[1].name").value(inv1.getName()))
                .andExpect(jsonPath("$[1].cost").value(inv1.getCost()))
                .andExpect(jsonPath("$[1].stock").value(inv1.getStock()))
                .andExpect(jsonPath("$[1].inventoryType").value(inv1.getInventoryType().toString()));
    }

    @Test
    void createInventoryItem() throws Exception {
        inventoryItemAddedDto = new InventoryItemAddedDto();
        inventoryItemAddedDto.name = "Interieur stomen";
        inventoryItemAddedDto.cost = 40.00;
        inventoryItemAddedDto.inventoryType = InventoryType.MANUAL_ACTION;

        mockMvc.perform(post("/api/inventory").header("Authorization", token).contentType(APPLICATION_JSON_UTF8).content(asJsonString(inventoryItemAddedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(inventoryItemAddedDto.name))
                .andExpect(jsonPath("cost").value(inventoryItemAddedDto.cost))
                .andExpect(jsonPath("inventoryType").value(inventoryItemAddedDto.inventoryType.toString()));
    }

    @Test
    void editInventoryItem() throws Exception {
        inventoryItemDto = new InventoryItemDto();
        inventoryItemDto.name = "Voorbumper repareren";
        inventoryItemDto.cost = 132.01;
        inventoryItemDto.inventoryType = InventoryType.MANUAL_ACTION;

        mockMvc.perform(put("/api/inventory/1").header("Authorization", token).contentType(APPLICATION_JSON_UTF8).content(asJsonString(inventoryItemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("inventoryItemId").value("1"))
                .andExpect(jsonPath("name").value(inventoryItemDto.name))
                .andExpect(jsonPath("cost").value(inventoryItemDto.cost))
                .andExpect(jsonPath("inventoryType").value(inventoryItemDto.inventoryType.toString()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}