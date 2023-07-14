package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.BringMomentDto;
import nl.minfin.eindopdracht.dto.MiscTaskDto;
import nl.minfin.eindopdracht.dto.PerformedTasksDto;
import nl.minfin.eindopdracht.objects.enums.InventoryType;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.models.InventoryItem;
import nl.minfin.eindopdracht.objects.models.Repair;
import nl.minfin.eindopdracht.objects.exceptions.*;
import nl.minfin.eindopdracht.objects.exceptions.FileNotFoundException;
import nl.minfin.eindopdracht.objects.models.File;
import nl.minfin.eindopdracht.objects.enums.RepairStatus;
import nl.minfin.eindopdracht.repositories.InventoryItemRepository;
import nl.minfin.eindopdracht.repositories.CustomerRepository;
import nl.minfin.eindopdracht.repositories.FileRepository;
import nl.minfin.eindopdracht.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RepairService {

    private @Autowired RepairRepository repairRepository;
    private @Autowired CustomerRepository customerRepository;
    private @Autowired InventoryItemRepository inventoryItemRepository;
    private @Autowired FileRepository fileRepository;

    public Repair createRepair(BringMomentDto bringMoment) {
        Optional<Customer> customer = customerRepository.findById(bringMoment.customerId);

        if (customer.isEmpty()) throw new CustomerNotExistsException(bringMoment.customerId);

        Repair repair = new Repair(customerRepository.findById(bringMoment.customerId).get(),
                bringMoment.bringDate);

        if (repair.getRepairDate() == null) throw new IncorrectSyntaxException("repairDate");

        return repairRepository.save(repair);
    }

    public Repair uploadFile(Long repairId, MultipartFile uploadedFile){
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        try {
            File file = new File(uploadedFile.getBytes(), uploadedFile.getOriginalFilename());

            fileRepository.save(file);
            repair.get().setFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return repairRepository.save(repair.get());
    }

    public ResponseEntity<Resource> downloadFile(Long repairId) throws java.io.FileNotFoundException {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        File downloadedFile = repair.get().getFile();

        if (downloadedFile == null) throw new FileNotFoundException(repairId);

        java.io.File file = new java.io.File(downloadedFile.getName());

        try {
            OutputStream os = new FileOutputStream(file);

            os.write(downloadedFile.getContent());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attatchment; filename=" + file.getName());
        return ResponseEntity.ok()
                .contentLength(file.length())
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    public Repair setFoundProblems(Long repairId, List<String> foundProblems) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        if (foundProblems == null) {
            throw new IncorrectSyntaxException("foundProblems");
        }

        String problemsFoundString = String.join(", ", foundProblems);

        repair.get().setProblemsFound(problemsFoundString);
        return repair.get();
    }

    public Repair setAgreed(Long repairId) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        repair.get().setCustomerAgreed(true);
        return repair.get();
    }

    public Repair setRepairDate(Long repairId, java.sql.Date repairDate) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);
        if (repairDate == null) throw new IncorrectSyntaxException("repairDate");
        if (repair.get().getProblemsFound() == null) throw new PreviousStepNotCompletedException("set found problems");
        if (!repair.get().getCustomerAgreed()) throw new CustomerNotAgreedException();

        repair.get().setCustomerAgreed(true);
        repair.get().setRepairDate(repairDate);
        return repairRepository.save(repair.get());
    }

    public Repair setNotAgreed(Long repairId) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        repair.get().setCustomerAgreed(false);
        repair.get().setCompleted(RepairStatus.CANCELED);
        repair.get().setPrice(45.00);
        return repairRepository.save(repair.get());
    }

    public String getReceipt(Long repairId) {
        Optional<Repair> repair = repairRepository.findById(repairId);
        double tax = 0.21;
        double total = 45.00;
        StringBuilder receipt = new StringBuilder();

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        receipt.append("Klantnummer: ").append(repair.get().getCustomer().getCustomerId());
        receipt.append("Kenteken: ").append(repair.get().getCustomer().getLicensePlate());
        receipt.append("Taken uitgevoerd: \n");
        receipt.append("\nKeuring   €").append(total);

        if (repair.get().getCompleted() != RepairStatus.CANCELED && repair.get().getCustomerAgreed()) {
            for (Integer id : repair.get().getPerformedTasks()) {
                InventoryItem inventoryItem = inventoryItemRepository.findInventoryItemByInventoryItemId(id);

                receipt.append("\n").append(inventoryItem.getName()).append("   €").append(inventoryItem.getCost());
                total += inventoryItem.getCost();
            }
        }

        receipt.append("\n\n=-=-=-=-=-=-=-=-=-=\n\n");
        receipt.append("Totaal exc: €").append(total).append(" | Totaal inc: €").append(total + (total * tax));

        return receipt.toString();

    }

    public Repair setPerformedTasks(Long repairId, PerformedTasksDto performedTasksDto) {
        if (performedTasksDto.performedTasks == null) throw new IncorrectSyntaxException("set:PerformedTasks");

        List<Integer> performedTasks = performedTasksDto.performedTasks;
        for (int inventoryItemId : performedTasks) {
            inventoryItemRepository.findById(inventoryItemId).map(inventoryItem -> {
                if (inventoryItem.getInventoryType() == InventoryType.CAR_PART && inventoryItem.getStock() <= 0) {
                    throw new ItemNoStockException(inventoryItemId);
                } else if (inventoryItem.getInventoryType() == InventoryType.CAR_PART) {
                    inventoryItem.setStock(inventoryItem.getStock() - 1);
                }

                return inventoryItemRepository.save(inventoryItem);
            }).orElseThrow(() -> new InventoryItemNotExistsException(inventoryItemId));
        }

        if (performedTasks.isEmpty()) performedTasks.add(-1);

        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);
        if (repair.get().getCustomerAgreed() == null) throw new PreviousStepNotCompletedException("set repair date (if customer agrees)");
        if (!repair.get().getCustomerAgreed()) throw new CustomerNotAgreedException();

        repair.get().setPerformedTasks(performedTasks);
        return repairRepository.save(repair.get());
    }

    public Repair setMiscTask(Long repairId, MiscTaskDto miscTaskDto) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        repair.get().setMiscellaneousTaskPrice(miscTaskDto.price);
        return repairRepository.save(repair.get());
    }

    public void deleteRepair(Long repairId) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        repairRepository.deleteById(repairId);
    }

    public Repair setRepairToCompleted(Long repairId) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        repair.get().setCompleted(RepairStatus.COMPLETED);
        return repairRepository.save(repair.get());
    }

    public Repair setRepairToPaid(Long repairId) {
        Optional<Repair> repair = repairRepository.findById(repairId);

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        repair.get().setPaid(true);
        return repairRepository.save(repair.get());
    }

}
