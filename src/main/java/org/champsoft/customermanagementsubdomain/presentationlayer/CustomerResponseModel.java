package org.champsoft.customermanagementsubdomain.presentationlayer;



import lombok.*;
import org.champsoft.customermanagementsubdomain.dataaccesslayer.PhoneNumber;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResponseModel   {
    private String customerId;
    private String lastName;
    private String firstName;
    private String emailAddress;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String province;
    private List<PhoneNumber> phoneNumbers;
//    private String phoneType;
//    private String phoneNumber;

}
