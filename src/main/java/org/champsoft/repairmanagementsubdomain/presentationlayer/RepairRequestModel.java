package org.champsoft.repairmanagementsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.RepairCost;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.RepairStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequestModel {

    private String orderId;
    private String repairId;
    private String description;
    private LocalDateTime repairDate;
    private BigDecimal amount;
    private String currency;
   private RepairStatus repairStatus;
}
