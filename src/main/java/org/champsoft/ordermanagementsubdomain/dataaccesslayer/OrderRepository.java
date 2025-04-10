package org.champsoft.ordermanagementsubdomain.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findOrderByOrderIdentifier_OrderId(String orderId);

    boolean existsByOrderIdentifier_OrderId(String orderId);

    boolean existsByWatchIdentifier_WatchId(String watchId);

    boolean existsByWatchIdentifier_WatchIdAndOrderStatus(String watchId, OrderStatus orderStatus);
}
