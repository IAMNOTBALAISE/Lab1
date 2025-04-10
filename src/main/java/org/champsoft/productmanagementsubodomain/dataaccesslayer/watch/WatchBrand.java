package org.champsoft.productmanagementsubodomain.dataaccesslayer.watch;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@NoArgsConstructor
@Getter
@Setter
public class WatchBrand {

    @Column(name = "brand_name",nullable = false)
    private String brandName;

    @Column(name = "brand_country",nullable = false)
    private String brandCountry;



}
