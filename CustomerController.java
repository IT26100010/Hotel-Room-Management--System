package com.hotel.controller;

import com.hotel.model.Customer;
import com.hotel.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    // Constructor Injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 1. VIEW ALL CUSTOMERS
    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    // 2. SEARCH CUSTOMER BY ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {

        return customerService.getCustomerById(id);
    }

    // 3. ADD NEW CUSTOMER
    @PostMapping
    public ResponseEntity<Customer> addCustomer(
            @RequestBody Customer customer) {

        Customer savedCustomer =
                customerService.addCustomer(customer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCustomer);
    }

    // 4. UPDATE CUSTOMER
    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable String id,
            @RequestBody Customer customer) {

        return customerService.updateCustomer(id, customer);
    }

    // 5. DELETE CUSTOMER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable String id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    // Handle validation and customer errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(
            IllegalArgumentException exception) {

        String message = exception.getMessage();

        HttpStatus status = "Customer not found.".equals(message)
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(Map.of("error", message));
    }
}