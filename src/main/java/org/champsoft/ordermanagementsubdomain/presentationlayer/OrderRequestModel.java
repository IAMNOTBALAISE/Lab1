package org.champsoft.ordermanagementsubdomain.presentationlayer;

import lombok.*;
import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class OrderRequestModel {

    private String orderId;
    private String customerId;
    private String catalogId;
    private String watchId;
    private String servicePlanId;
    private Double salePrice;
    private String currency;
    private String paymentCurrency;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
}
