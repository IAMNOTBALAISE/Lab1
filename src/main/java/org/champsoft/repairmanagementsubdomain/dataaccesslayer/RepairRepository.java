package org.champsoft.repairmanagementsubdomain.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairRepository extends JpaRepository<Repair, Integer> {

    List<Repair> findByOrderIdentifier_OrderId(String orderId);

  // Repair findByOrderIdentifier_OrderId(String orderId);

    Repair findByRepairIdentifier_RepairId(String repairId);
}
