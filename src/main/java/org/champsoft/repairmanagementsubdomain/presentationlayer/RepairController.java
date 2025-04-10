package org.champsoft.repairmanagementsubdomain.presentationlayer;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.champsoft.repairmanagementsubdomain.businesslogiclayer.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/repairs")
public class RepairController {

    private final RepairService repairService;

    @Autowired
    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @GetMapping
    public ResponseEntity<List<RepairResponseModel>> getAllRepairs() {
        return ResponseEntity.ok(repairService.getAllRepairs());
    }


    @GetMapping("/{repairId}")
    public ResponseEntity<RepairResponseModel> getRepairById(@PathVariable String repairId) {
        return ResponseEntity.ok(repairService.getRepairById(repairId));
    }


    @PostMapping
    public ResponseEntity<RepairResponseModel> createRepair(@RequestBody RepairRequestModel repairRequestModel) {
        RepairResponseModel createdRepair = repairService.createRepair(repairRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRepair);
    }


    @PutMapping("/{repairId}")
    public ResponseEntity<RepairResponseModel> updateRepair(@PathVariable String repairId,
                                                            @RequestBody RepairRequestModel repairRequestModel) {
        RepairResponseModel updatedRepair = repairService.updateRepair(repairId, repairRequestModel);
        return ResponseEntity.ok(updatedRepair);
    }


    @DeleteMapping("/{repairId}")
    public ResponseEntity<String> deleteRepair(@PathVariable String repairId) {
        repairService.deleteRepair(repairId);
        return ResponseEntity.ok("Repair with ID " + repairId + " deleted successfully.");
    }

}
