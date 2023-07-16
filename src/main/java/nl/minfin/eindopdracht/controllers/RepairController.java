package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.dto.*;
import nl.minfin.eindopdracht.objects.models.Repair;
import nl.minfin.eindopdracht.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/repairs")
public class RepairController {

    private @Autowired RepairService repairService;

    // Endpoint om een nieuwe repareer actie aan te maken
    @PostMapping
    Repair createRepair(@RequestBody BringMomentDto bringMoment) { return repairService.createRepair(bringMoment); }

    // Endpoint om een bestand toe te voegen aan een reparatie
    @PutMapping(path = "/files/{repairId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Repair addFilesToRepair(@PathVariable long repairId, @RequestPart("file") MultipartFile uploadedFile) {
        return repairService.uploadFile(repairId, uploadedFile);
    }

    // Endpoint om een bestand terug te krijgen wat aan een reparatie is gelinkt
    @GetMapping(path = "/files/{repairId}")
    ResponseEntity<Resource> downloadFile(@PathVariable long repairId) throws FileNotFoundException {
        return repairService.downloadFile(repairId);
    }

    // Endpoint om alle gevonden problemen te noteren voor een reparatie
    @PutMapping("/{repairId}/problemsFound")
    Repair addProblemsFoundToRepair(@PathVariable long repairId, @RequestBody ProblemsFoundDto problemsFound) {
        return repairService.setFoundProblems(repairId, problemsFound.problemsFound);
    }

    // Endpoint om aan te geven dat de klant akkoord is met de reparatie
    @PutMapping("/{repairId}/setAgreed")
    Repair setRepairToAgreed(@PathVariable long repairId) { return repairService.setAgreed(repairId); }

    // Endpoint om de reparatie datum vast te stellen
    @PutMapping("/{repairId}/setRepairDate")
    Repair setRepairToAgreed(@PathVariable long repairId, @RequestBody RepairDateDto repairDate) {
        return repairService.setRepairDate(repairId, repairDate.repairDate);
    }

    // Endpoint om aan te geven dat de klant niet akkoord is met de reparatie
    @PutMapping("/{repairId}/setNotAgreed")
    Repair setRepairToNotAgreed(@PathVariable long repairId) { return repairService.setNotAgreed(repairId); }

    // Endpoint om een bon te creeeren voor de klant, dit kan alleen role CASHIER
    @GetMapping("{repairId}/getReceipt")
    String getReceiptFromRepair(@PathVariable long repairId) { return repairService.getReceipt(repairId); }

    // Endpoint om de uitgevoerde taken te noteren, dit is een List<int>
    @PutMapping("/{repairId}/setPerformedTasks")
    Repair setPerformedTasks(@PathVariable long repairId, @RequestBody PerformedTasksDto performedTasksDto) {
        return repairService.setPerformedTasks(repairId, performedTasksDto);
    }

    // Endpoint om eventueel de overige taak in te voeren, dit is een double
    @PutMapping("/{repairId}/setMiscTask")
    Repair setMiscTask(@PathVariable long repairId, @RequestBody MiscTaskDto miscTaskDto) {
        return repairService.setMiscTask(repairId, miscTaskDto);
    }

    // Endpoint om een reparatie te verwijderen uit het systeem
    @DeleteMapping("/{repairId}/delete")
    void deleteRepair(@PathVariable long repairId) { repairService.deleteRepair(repairId); }

    // Endpoint om een reparatie te voltooien
    @PutMapping("/{repairId}/setCompleted")
    Repair setRepairCompleted(@PathVariable long repairId) { return repairService.setRepairToCompleted(repairId); }

    // Endpoint om aan te geven dat een klant heeft betaald voor de reparatie
    @PutMapping("/{repairId}/setPaid")
    Repair setRepairPaid(@PathVariable long repairId) { return repairService.setRepairToPaid(repairId); }

}
