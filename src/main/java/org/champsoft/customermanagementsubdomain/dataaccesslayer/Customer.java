package org.champsoft.customermanagementsubdomain.dataaccesslayer;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;


@Entity
@Table(name="customers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    //private identifier

    @Embedded
    private CustomerIdentifier customerIdentifier;

    private String lastName;
    private String firstName;
    private String emailAddress;
    private String username;
    private String password;
    @Embedded
    private Address customerAddress;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "customer_phonenumbers",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    )
    private List<PhoneNumber> phoneNumbers;



}

