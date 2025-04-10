package org.champsoft.productmanagementsubodomain.dataaccesslayer.watch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.CatalogIdentifier;

import java.util.List;

@Entity
@Table(name="watches")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Watch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private WatchIdentifier watchIdentifier;

    @Embedded
    private CatalogIdentifier catalogIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "watch_status")
    private WatchStatus watchStatus;

    @Enumerated(EnumType.STRING)
    private UsageType usageType;

    private String model;
    private String material;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "watch_accessories",joinColumns =
    @JoinColumn(name = "watch_id",referencedColumnName = "watch_id"))
    private List<Accessory> accessories;

    @Embedded
    private WatchBrand watchBrand;

    @Embedded
    private Price price;


}
