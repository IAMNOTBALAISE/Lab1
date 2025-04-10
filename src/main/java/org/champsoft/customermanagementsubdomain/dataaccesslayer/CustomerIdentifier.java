package org.champsoft.customermanagementsubdomain.dataaccesslayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CustomerIdentifier {


    @Column(name = "customer_id", nullable = false, unique = true)
    private String customerId;


    public CustomerIdentifier(String customerId) {
        this.customerId = (customerId != null && !customerId.isEmpty()) ? customerId : UUID.randomUUID().toString();
    }

  }

