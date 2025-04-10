package org.champsoft.repairmanagementsubdomain.dataaccesslayer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderIdentifier;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "repairs")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private RepairIdentifier repairIdentifier;

    private String description;
    private LocalDateTime repairDate;

    @Embedded
    private OrderIdentifier orderIdentifier;

    @Embedded
    private RepairCost repairCost;

    @Enumerated(EnumType.STRING)
    private RepairStatus repairStatus;



}
