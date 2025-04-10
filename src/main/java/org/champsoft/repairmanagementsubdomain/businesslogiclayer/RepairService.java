package org.champsoft.repairmanagementsubdomain.businesslogiclayer;

import org.champsoft.repairmanagementsubdomain.presentationlayer.RepairRequestModel;
import org.champsoft.repairmanagementsubdomain.presentationlayer.RepairResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RepairService {
    List<RepairResponseModel> getAllRepairs();

    RepairResponseModel getRepairById(String repairId);

    RepairResponseModel createRepair(RepairRequestModel repairRequestModel);

    RepairResponseModel updateRepair(String repairId, RepairRequestModel repairRequestModel);

    String deleteRepair(String repairId);
}
