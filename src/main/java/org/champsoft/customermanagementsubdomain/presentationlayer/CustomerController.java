package org.champsoft.customermanagementsubdomain.presentationlayer;


import org.champsoft.customermanagementsubdomain.businesslogiclayer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponseModel>> getCustomers() {
        return ResponseEntity.ok(this.customerService.getCustomers());
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<CustomerResponseModel> getCustomerById(@PathVariable String customer_id) {
        return ResponseEntity.ok(this.customerService.getCustomerbyCustomerId(customer_id));
    }

    @GetMapping(params = "email")
    public ResponseEntity<CustomerResponseModel> getCustomerByEmail(@RequestParam String email) {

        return ResponseEntity.ok(this.customerService.getCustomerbyEmail(email));
    }

    @PostMapping()
    public ResponseEntity<CustomerResponseModel> addCustomer(@RequestBody CustomerRequestModel newCustomerData) {
        return ResponseEntity.ok(this.customerService.addCustomer(newCustomerData));
    }

    @PutMapping("/{customer_id}")
    public ResponseEntity<CustomerResponseModel> updateCustomer(
            @PathVariable String customer_id,
            @RequestBody CustomerRequestModel newCustomerData) {
        return ResponseEntity.ok(this.customerService.updateCustomer(customer_id, newCustomerData));
    }

    @DeleteMapping("/{customer_id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable String customer_id) {
        return ResponseEntity.ok(this.customerService.deleteCustomerbyCustomerId(customer_id));
    }
}
