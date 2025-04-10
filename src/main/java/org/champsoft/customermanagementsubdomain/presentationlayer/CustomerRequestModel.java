package org.champsoft.customermanagementsubdomain.presentationlayer;



import lombok.*;
import org.champsoft.customermanagementsubdomain.dataaccesslayer.PhoneNumber;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerRequestModel {
    private String customerId;
    private String lastName;
    private String firstName;
    private String emailAddress;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String province;
    private String  username;
    private String  password1;
    private String  password2;
    private List<PhoneNumber> phoneNumbers;
}
