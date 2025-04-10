package org.champsoft.repairmanagementsubdomain.presentationlayer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.RepairCost;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.RepairStatus;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairResponseModel extends RepresentationModel<RepairResponseModel> {


    private String repairId;
    private String orderId;
    private String description;
    private LocalDateTime repairDate;
    private BigDecimal amount;
    private String currency;
    private RepairStatus repairStatus;

}
