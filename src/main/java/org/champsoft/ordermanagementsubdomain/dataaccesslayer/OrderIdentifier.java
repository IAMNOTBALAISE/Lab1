package org.champsoft.ordermanagementsubdomain.dataaccesslayer;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class OrderIdentifier {


    @Column(name = "order_id",nullable = false,unique = true)
    private String orderId;

    public OrderIdentifier(String orderId) {
        this.orderId = (orderId != null && !orderId.isEmpty()) ? orderId : UUID.randomUUID().toString();
    }



}
