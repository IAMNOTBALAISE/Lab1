package org.champsoft.ordermanagementsubdomain.businesslogiclayer;


import org.champsoft.customermanagementsubdomain.businesslogiclayer.CustomerServiceClient;
import org.champsoft.customermanagementsubdomain.dataaccesslayer.CustomerIdentifier;
import org.champsoft.customermanagementsubdomain.presentationlayer.CustomerResponseModel;
import org.champsoft.ordermanagementsubdomain.dataaccesslayer.*;
import org.champsoft.ordermanagementsubdomain.datamapperlayer.OrderRequestMapper;
import org.champsoft.ordermanagementsubdomain.datamapperlayer.OrderResponseMapper;
import org.champsoft.ordermanagementsubdomain.presentationlayer.OrderRequestModel;
import org.champsoft.ordermanagementsubdomain.presentationlayer.OrderResponseModel;
import org.champsoft.productmanagementsubodomain.businesslogiclayer.CatalogServiceClient;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.Catalog;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.CatalogIdentifier;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.WatchIdentifier;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.WatchStatus;
import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogResponseModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchRequestModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchResponseModel;
import org.champsoft.serviceplanmanagementsubdomain.businesslogiclayer.ServicePlanServiceClient;
import org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer.ServicePlanIdentifier;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanResponseModel;
import org.champsoft.utils.InvalidInputException;
import org.champsoft.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderRequestMapper orderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;
    private final CustomerServiceClient customerServiceClient;
    private final CatalogServiceClient catalogServiceClient;
    private final ServicePlanServiceClient servicePlanServiceClient;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,OrderRequestMapper orderRequestMapper,
                            OrderResponseMapper orderResponseMapper, CustomerServiceClient customerServiceClient,
                            CatalogServiceClient catalogServiceClient, ServicePlanServiceClient servicePlanServiceClient){

        this.orderRepository = orderRepository;
        this.orderRequestMapper = orderRequestMapper;
        this.orderResponseMapper = orderResponseMapper;
        this.customerServiceClient = customerServiceClient;
        this.catalogServiceClient = catalogServiceClient;
        this.servicePlanServiceClient = servicePlanServiceClient;

    }

    @Override
    public List<OrderResponseModel> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        orders.forEach(this::updateWatchStatusIfNeeded);

        return orderResponseMapper.entityToResponseModelList(orders);
    }

    @Override
    public OrderResponseModel getOrderById(String orderId) {

        Order order = orderRepository.findOrderByOrderIdentifier_OrderId(orderId);
        if(order == null){
            throw new NotFoundException("Order ID " + orderId + " not found");

        }

        updateWatchStatusIfNeeded(order);

        return orderResponseMapper.entityToResponseModel(order);
    }

    @Override
    public OrderResponseModel updateOrder(String orderId, OrderRequestModel orderRequestModel){

        Order existingOrder = orderRepository.findOrderByOrderIdentifier_OrderId(orderId);

        if(existingOrder == null){
            throw new NotFoundException("Order ID " + orderId + " not found");

        }

        OrderStatus originalStatus = existingOrder.getOrderStatus();

        existingOrder.setOrderStatus(orderRequestModel.getOrderStatus());
        existingOrder.setOrderDate(orderRequestModel.getOrderDate());
        existingOrder.setPrice(new Price(
                new BigDecimal(orderRequestModel.getSalePrice()),
                stringToCurrency(orderRequestModel.getCurrency()),
                stringToCurrency(orderRequestModel.getPaymentCurrency())
        ));


        Order updateOrder = orderRepository.save(existingOrder);

        if(originalStatus != orderRequestModel.getOrderStatus()){
            updateWatchStatus(orderRequestModel,updateOrder);
        }

        return orderResponseMapper.entityToResponseModel(updateOrder);



        }



    @Override
    public OrderResponseModel createOrder(OrderRequestModel orderRequestModel) {

        CustomerResponseModel customerResponseModel = customerServiceClient.getCustomerByCustomerId(orderRequestModel.getCustomerId());

        if(customerResponseModel == null){
            throw new NotFoundException("Customer ID " + orderRequestModel.getCustomerId() + " not found");
        }

        WatchResponseModel watchResponseModel = catalogServiceClient.getWatchByWatchId(orderRequestModel.getCatalogId(), orderRequestModel.getWatchId());
        if(watchResponseModel == null){
            throw new NotFoundException("Watch ID " + orderRequestModel.getCatalogId() + " not found");
        } else if (orderRequestModel.getCatalogId() == null || orderRequestModel.getCatalogId().isEmpty()) {
            throw new NotFoundException("Catalog ID " + orderRequestModel.getCatalogId() + " not found");

        }

        ServicePlanResponseModel servicePlanResponseModel = servicePlanServiceClient.getServicePlanById(orderRequestModel.getServicePlanId());
        if(servicePlanResponseModel == null){
            throw new NotFoundException("Service Plan ID " + orderRequestModel.getServicePlanId() + " not found");
        }
        if (orderRepository.existsByWatchIdentifier_WatchIdAndOrderStatus(orderRequestModel.getWatchId(), OrderStatus.PURCHASE_COMPLETED)) {
            throw new InvalidInputException("Watch ID " + orderRequestModel.getWatchId() + " has already been sold and cannot be ordered again.");
        }

        boolean watchInUse = orderRepository.existsByWatchIdentifier_WatchId(orderRequestModel.getWatchId());
        if (watchInUse) {
            throw new InvalidInputException("Watch ID " + orderRequestModel.getWatchId() + " is already associated with an existing order.");
        }

        Order order = this.orderRequestMapper.requestModelToEntity(orderRequestModel);

        order.setOrderIdentifier(new OrderIdentifier(orderRequestModel.getOrderId()));
        order.setCustomerIdentifier(new CustomerIdentifier(orderRequestModel.getCustomerId()));
        order.setCatalogIdentifier(new CatalogIdentifier(orderRequestModel.getCatalogId()));
        order.setWatchIdentifier(new WatchIdentifier(orderRequestModel.getWatchId()));
        order.setServicePlanIdentifier(new ServicePlanIdentifier(orderRequestModel.getServicePlanId()));

        if (orderRequestModel.getOrderStatus() == null) {
            order.setOrderStatus(OrderStatus.PURCHASE_NEGOTIATION); // Set a default status if null
        } else {
            order.setOrderStatus(orderRequestModel.getOrderStatus()); // Set provided status
        }

        Price price = new Price(
                new BigDecimal(orderRequestModel.getSalePrice()),
                stringToCurrency(orderRequestModel.getCurrency().toString()),
                stringToCurrency(orderRequestModel.getPaymentCurrency())
        );

        order.setPrice(price);

        Order saved = orderRepository.save(order);
        if(saved == null){

            throw new InvalidInputException("An error has occur while saving");
        }

        updateWatchStatus(orderRequestModel,saved);
        return this.orderResponseMapper.entityToResponseModel(saved);

    }




    private Currency stringToCurrency(String stringVar) {

        // How confident are you that stringVar will have an acceptable value?
        Currency enumValue;
        try {
            // Consider using trim to eliminate extraneous whitespace
            enumValue = Currency.valueOf(stringVar.trim());
        } catch (Exception e) {
            // handle the situation here. Here are a couple of ideas.
            // Apply null and expect the using code to detect.
            // enumValue = null;
            // Have a defined private constant for a default value
            // assuming a default value would make more sense than null
            enumValue = Currency.CAD;
        }
        return enumValue;

    }

    private void updateWatchStatus(OrderRequestModel orderRequestModel, Order saved) {

        WatchStatus watchStatus;

        switch (saved.getOrderStatus()) {
            case PURCHASE_COMPLETED -> watchStatus = WatchStatus.SOLD_OUT;
            case PURCHASE_CANCELED -> watchStatus = WatchStatus.AVAILABLE;
            default -> watchStatus = WatchStatus.SALE_PENDING;
        }

        WatchResponseModel watchResponseModel = catalogServiceClient.getWatchByWatchId(
                orderRequestModel.getCatalogId(),
                orderRequestModel.getWatchId()
        );

        WatchRequestModel watchRequestModel = WatchRequestModel.builder()
                .watchId(watchResponseModel.getWatchId())
                .catalogId(watchResponseModel.getCatalogId())
                .watchStatus(watchStatus)
                .usageType(watchResponseModel.getUsageType())
                .model(watchResponseModel.getModel())
                .material(watchResponseModel.getMaterial())
                .accessories(watchResponseModel.getAccessories())
                .price(watchResponseModel.getPrice())
                .watchBrand(watchResponseModel.getWatchBrand())
                .build();

        catalogServiceClient.updateWatchStatus(
                watchRequestModel,
                orderRequestModel.getCatalogId(),
                orderRequestModel.getWatchId()
        );

    }

    @Override
    public String deleteOrder(String orderId){


        Order order = orderRepository.findOrderByOrderIdentifier_OrderId(orderId);
        if (order == null) {
            throw new NotFoundException("Order ID " + orderId + " not found");
        }


        WatchResponseModel watchResponseModel = catalogServiceClient.getWatchByWatchId(
                order.getCatalogIdentifier().getCatalogId(),
                order.getWatchIdentifier().getWatchId()
        );





            WatchRequestModel watchRequestModel = WatchRequestModel.builder()
                    .watchId(watchResponseModel.getWatchId())
                    .catalogId(watchResponseModel.getCatalogId())
                    .watchStatus(WatchStatus.AVAILABLE)
                    .usageType(watchResponseModel.getUsageType())
                    .model(watchResponseModel.getModel())
                    .material(watchResponseModel.getMaterial())
                    .accessories(watchResponseModel.getAccessories())
                    .price(watchResponseModel.getPrice())
                    .watchBrand(watchResponseModel.getWatchBrand())
                    .build();


            catalogServiceClient.updateWatchStatus(watchRequestModel,
                    order.getCatalogIdentifier().getCatalogId(),
                    order.getWatchIdentifier().getWatchId());

        // Delete the order
        orderRepository.delete(order);

        return "Order with ID " + orderId + " deleted successfully. Watch status reset to AVAILABLE.";

    }

    private void updateWatchStatusIfNeeded(Order order) {
        if (order.getOrderStatus() == OrderStatus.PURCHASE_COMPLETED) {
            WatchResponseModel watchResponseModel = catalogServiceClient.getWatchByWatchId(
                    order.getCatalogIdentifier().getCatalogId(),
                    order.getWatchIdentifier().getWatchId()
            );

            WatchRequestModel watchRequestModel = WatchRequestModel.builder()
                    .watchId(watchResponseModel.getWatchId())
                    .catalogId(watchResponseModel.getCatalogId())
                    .watchStatus(WatchStatus.SOLD_OUT) // Force update to SOLD_OUT
                    .usageType(watchResponseModel.getUsageType())
                    .model(watchResponseModel.getModel())
                    .material(watchResponseModel.getMaterial())
                    .accessories(watchResponseModel.getAccessories())
                    .price(watchResponseModel.getPrice())
                    .watchBrand(watchResponseModel.getWatchBrand())
                    .build();

            catalogServiceClient.updateWatchStatus(
                    watchRequestModel,
                    order.getCatalogIdentifier().getCatalogId(),
                    order.getWatchIdentifier().getWatchId()
            );
        }
    }

}
