package org.champsoft.ordermanagementsubdomain.dataaccesslayer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.champsoft.customermanagementsubdomain.dataaccesslayer.CustomerIdentifier;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.CatalogIdentifier;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.WatchIdentifier;
import org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer.ServicePlanIdentifier;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private OrderIdentifier orderIdentifier;

    @Embedded
    private CustomerIdentifier customerIdentifier;

    @Embedded
    private WatchIdentifier watchIdentifier;

    @Embedded
    private ServicePlanIdentifier servicePlanIdentifier;

    @Embedded
    private CatalogIdentifier catalogIdentifier;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Embedded
    private Price price;

    @Embedded
    private Currency currency;

    private LocalDateTime orderDate;

}
