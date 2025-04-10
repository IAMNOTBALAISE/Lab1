package org.champsoft.ordermanagementsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderStatus;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderResponseModel extends RepresentationModel<OrderResponseModel> {

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
