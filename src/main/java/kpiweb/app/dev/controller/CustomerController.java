package kpiweb.app.dev.controller;


import jakarta.validation.Valid;
import kpiweb.app.dev.entity.Customer;
import kpiweb.app.dev.exception.ResourceNotFoundException;
import kpiweb.app.dev.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer cust) {
        Customer created = customerService.createCustomer(cust);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String cubeIdPk) {
        Customer cust = customerService.getCustomerById(cubeIdPk)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "cubeIdPk", cubeIdPk));
        return ResponseEntity.ok(cust);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> all = customerService.getAllCustomers();
        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(all);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") String cubeIdPk,
                                                   @Valid @RequestBody Customer details) {
        Customer updated = customerService.updateCustomer(cubeIdPk, details);
        return ResponseEntity.ok(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String cubeIdPk) {
        customerService.deleteCustomer(cubeIdPk);
        return ResponseEntity.noContent().build();
    }
}
