package org.champsoft.repairmanagementsubdomain.datamapperlayer;

import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderIdentifier;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.Repair;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.RepairCost;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.RepairIdentifier;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.RepairStatus;
import org.champsoft.repairmanagementsubdomain.presentationlayer.RepairRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RepairRequestMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true), // Auto-generated ID
            @Mapping(expression = "java(repairIdentifier)", target = "repairIdentifier"),
            @Mapping(expression = "java(orderIdentifier)", target = "orderIdentifier"),
            @Mapping(expression = "java(repairCost)", target = "repairCost"),
            @Mapping(expression = "java(repairStatus)", target = "repairStatus"),
            @Mapping(target = "repairDate", expression = "java(java.time.LocalDateTime.now())") // Set current date-time
    })
    Repair requestModelToEntity(
            RepairRequestModel repairRequestModel,
            RepairIdentifier repairIdentifier,
            OrderIdentifier orderIdentifier,
            RepairCost repairCost,
            RepairStatus repairStatus
    );
}
