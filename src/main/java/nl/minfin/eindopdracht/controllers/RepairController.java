package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.dto.BringMomentDto;
import nl.minfin.eindopdracht.dto.ProblemsFoundDto;
import nl.minfin.eindopdracht.dto.RepairDateDto;
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

    @Autowired
    private RepairService repairService;

    @PostMapping
    Repair createRepair(@RequestBody BringMomentDto bringMoment) { return repairService.createRepair(bringMoment); }

    @PutMapping(path = "/files/{repairId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Repair addFilesToRepair(@PathVariable long repairId, @RequestPart MultipartFile uploadedFile) {
        return repairService.uploadFile(repairId, uploadedFile);
    }

    @GetMapping(path = "/files/{repairId}")
    ResponseEntity<Resource> downloadFile(@PathVariable long repairId) throws FileNotFoundException {
        return repairService.downloadFile(repairId);
    }

    @PutMapping("/{repairId}/problemsFound")
    Repair addProblemsFoundToRepair(@PathVariable long repairId, @RequestBody ProblemsFoundDto problemsFound) {
        return repairService.setFoundProblems(repairId, problemsFound.problemsFound);
    }

    @PutMapping("/{repairId}/setAgreed")
    Repair setRepairToAgreed(@PathVariable long repairId) { return repairService.setAgreed(repairId); }

    @PutMapping("/{repairId}/setRepairDate")
    Repair setRepairToAgreed(@PathVariable long repairId, @RequestBody RepairDateDto repairDate) {
        return repairService.setRepairDate(repairId, repairDate.repairDate);
    }

    @PutMapping("/{repairId}/setNotAgreed")
    Repair setRepairToNotAgreed(@PathVariable long repairId) { return repairService.setNotAgreed(repairId); }

    @GetMapping("{repairId}/getReceipt")
    String getReceiptFromRepair(@PathVariable long repairId) { return repairService.getReceipt(repairId); }



}
