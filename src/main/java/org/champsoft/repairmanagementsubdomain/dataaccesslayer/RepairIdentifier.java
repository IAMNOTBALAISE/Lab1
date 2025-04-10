package org.champsoft.repairmanagementsubdomain.dataaccesslayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class RepairIdentifier {

    @Column(name = "repair_id", nullable = false, unique = true)
    private String repairId;


    public RepairIdentifier(String repairId) {
        this.repairId = (repairId != null && !repairId.isEmpty()) ? repairId : UUID.randomUUID().toString();
    }
}
