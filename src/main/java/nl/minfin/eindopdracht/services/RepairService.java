package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.BringMomentDto;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.models.Repair;
import nl.minfin.eindopdracht.objects.exceptions.*;
import nl.minfin.eindopdracht.objects.exceptions.FileNotFoundException;
import nl.minfin.eindopdracht.objects.models.File;
import nl.minfin.eindopdracht.objects.enums.RepairStatus;
import nl.minfin.eindopdracht.repositories.CostItemRepository;
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
    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CostItemRepository costItemRepository;

    @Autowired
    private FileRepository fileRepository;

    // Returns all repairs.
    public List<Repair> getAllRepairs() {
        return repairRepository.findAll();
    }

    public Repair createRepair(BringMomentDto bringMoment) {
        Optional<Customer> customer = customerRepository.findById(bringMoment.customerId);

        if (customer.isEmpty()) throw new CustomerNotFoundException(bringMoment.customerId);

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
        if (repair.get().getProblemsFound() == null) throw new PreviousStepUncompletedException("set found problems");
        if (!repair.get().getCustomerAgreed()) throw new CustomerDisagreedException();

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
        repair.get().setPickupDate(new Date(System.currentTimeMillis()));
        return repairRepository.save(repair.get());
    }

    public String getReceipt(Long repairId) {
        Optional<Repair> repair = repairRepository.findById(repairId);
        double tax = 0.21;
        double total = 45.00;
        String receipt = "";

        if (repair.isEmpty()) throw new RepairNotExistsException(repairId);

        receipt += "Klantnummer: " + repair.get().getCustomer().getCustomerId();
        receipt += "Kenteken: " + repair.get().getCustomer().getLicensePlate();

        if (repair.get().getCompleted() == RepairStatus.CANCELED && !repair.get().getCustomerAgreed()) {
            receipt += "\nAlleen keuring          " + total;
        } else {

        }

        receipt += "\n=-=-=-=-=-=-=-=-=-=\n";
        receipt += "Totaal exc: €" + total + " | Totaal inc: €" + (total + (total*tax));

        return receipt;

    }
}
