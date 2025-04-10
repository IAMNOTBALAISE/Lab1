package org.champsoft.repairmanagementsubdomain.businesslogiclayer;

import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderIdentifier;
import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderRepository;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.*;
import org.champsoft.repairmanagementsubdomain.datamapperlayer.RepairRequestMapper;
import org.champsoft.repairmanagementsubdomain.datamapperlayer.RepairResponseMapper;
import org.champsoft.repairmanagementsubdomain.presentationlayer.RepairRequestModel;
import org.champsoft.repairmanagementsubdomain.presentationlayer.RepairResponseModel;
import org.champsoft.utils.InvalidInputException;
import org.champsoft.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {

    private final RepairRepository repairRepository;
    private final RepairRequestMapper repairRequestMapper;
    private final RepairResponseMapper repairResponseMapper;
    private final OrderRepository orderRepository;

@Autowired
public RepairServiceImpl(RepairRepository repairRepository,RepairRequestMapper repairRequestMapper,RepairResponseMapper repairResponseMapper,
                         OrderRepository orderRepository) {
    this.repairRepository = repairRepository;
    this.repairRequestMapper = repairRequestMapper;
    this.repairResponseMapper = repairResponseMapper;
    this.orderRepository = orderRepository;
}

    @Override
    public List<RepairResponseModel> getAllRepairs() {


        List<Repair> repairs = repairRepository.findAll();

        if (repairs.isEmpty()) {
            throw new NotFoundException("No repairs found. Some orders may have been deleted.");
        }

        return repairResponseMapper.entityListToResponseModelList(repairRepository.findAll());
    }


    @Override
    public RepairResponseModel getRepairById(String repairId) {
        Repair repair = repairRepository.findByRepairIdentifier_RepairId(repairId);

        if (repair == null) {
            throw new NotFoundException("Repair with ID " + repairId + " not found");
        }

        if (repair.getOrderIdentifier() == null || repair.getOrderIdentifier().getOrderId() == null) {
            throw new NotFoundException("Associated order for Repair ID " + repairId + " does not exist");
        }

        return repairResponseMapper.entityToResponseModel(repair);
    }

    @Override
    public RepairResponseModel createRepair(RepairRequestModel repairRequestModel) {
        if (repairRequestModel.getOrderId() == null || repairRequestModel.getOrderId().isEmpty()) {
            throw new InvalidInputException("Order ID is required for a repair.");
        }

        // Verify if the order still exists
        if (!orderRepository.existsByOrderIdentifier_OrderId(repairRequestModel.getOrderId())) {
            throw new NotFoundException("Cannot create repair. Order with ID " + repairRequestModel.getOrderId() + " does not exist.");
        }

        // Check if a repair with this ID already exists
        if (repairRequestModel.getRepairId() != null) {
            Repair foundRepair = repairRepository.findByRepairIdentifier_RepairId(repairRequestModel.getRepairId());
            if (foundRepair != null) {
                throw new InvalidInputException("Repair with ID " + repairRequestModel.getRepairId() + " already exists.");
            }
        }

        RepairIdentifier repairIdentifier = new RepairIdentifier(repairRequestModel.getRepairId());
        OrderIdentifier orderIdentifier = new OrderIdentifier(repairRequestModel.getOrderId());

        // Ensure repair cost is properly created
        RepairCost repairCost = new RepairCost(
                repairRequestModel.getAmount(),
                repairRequestModel.getCurrency()
        );

        Repair repair = repairRequestMapper.requestModelToEntity(
                repairRequestModel,
                repairIdentifier,
                orderIdentifier,
                repairCost,
                repairRequestModel.getRepairStatus()
        );

        Repair savedRepair = repairRepository.save(repair);
        return repairResponseMapper.entityToResponseModel(savedRepair);
    }


    @Override
    public RepairResponseModel updateRepair(String repairId, RepairRequestModel repairRequestModel) {
        Repair existingRepair = repairRepository.findByRepairIdentifier_RepairId(repairId);

        if (existingRepair == null) {
            throw new NotFoundException("Repair with ID " + repairId + " not found");
        }

        if (existingRepair.getOrderIdentifier() == null || existingRepair.getOrderIdentifier().getOrderId() == null) {
            throw new NotFoundException("Associated order for Repair ID " + repairId + " does not exist");
        }

        // Preserve existing repair ID
        RepairIdentifier repairIdentifier = new RepairIdentifier(existingRepair.getRepairIdentifier().getRepairId());

        // Update fields only if new values are provided
        if (repairRequestModel.getDescription() != null) {
            existingRepair.setDescription(repairRequestModel.getDescription());
        }

        if (repairRequestModel.getRepairDate() != null) {
            existingRepair.setRepairDate(repairRequestModel.getRepairDate());
        }

        // Ensure repair cost is updated correctly
        if (repairRequestModel.getAmount() != null && repairRequestModel.getCurrency() != null) {
            existingRepair.setRepairCost(new RepairCost(
                    repairRequestModel.getAmount(),
                    repairRequestModel.getCurrency()
            ));
        }

        // Ensure status is properly converted and updated
        if (repairRequestModel.getRepairStatus() != null) {
            try {
                RepairStatus newStatus = RepairStatus.valueOf(repairRequestModel.getRepairStatus().toString().toUpperCase());
                existingRepair.setRepairStatus(newStatus);
            } catch (IllegalArgumentException e) {
                throw new InvalidInputException("Invalid repair status: " + repairRequestModel.getRepairStatus());
            }
        }

        // Save and return updated repair
        Repair updatedRepair = repairRepository.save(existingRepair);
        return repairResponseMapper.entityToResponseModel(updatedRepair);
    }


    @Override
    public String deleteRepair(String repairId) {

        Repair repair = repairRepository.findByRepairIdentifier_RepairId(repairId);

        if (repair == null) {
            throw new NotFoundException("Repair with ID " + repairId + " not found");
        }

        if (repair.getOrderIdentifier() == null || repair.getOrderIdentifier().getOrderId() == null) {
            throw new NotFoundException("Associated order for Repair ID " + repairId + " does not exist");
        }

        repairRepository.delete(repair);
        return "Repair with ID " + repairId + " was deleted";
    }
    }

