package org.champsoft.customermanagementsubdomain.dataaccesslayer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Address {
    private String streetAddress;
    private String postalCode;
    private String city;
    private String province;
}
