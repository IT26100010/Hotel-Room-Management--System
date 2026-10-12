package com.hotel.service;

import com.hotel.model.Customer;
import com.hotel.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Constructor injection
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // View all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Search for a customer
    public Customer getCustomerById(String customerId) {

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer ID cannot be empty."
            );
        }

        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer not found."
                ));
    }

    // Add a new customer
    public Customer addCustomer(Customer customer) {

        validateCustomer(customer);

        if (customerRepository.findById(
                customer.getCustomerId()).isPresent()) {

            throw new IllegalArgumentException(
                    "Customer ID already exists."
            );
        }

        return customerRepository.save(customer);
    }

    // Update a customer
    public Customer updateCustomer(
            String customerId,
            Customer updatedCustomer) {

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer ID cannot be empty."
            );
        }

        validateCustomer(updatedCustomer);

        Customer existingCustomer = getCustomerById(customerId);

        // Keep the existing ID and update the other details
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setAddress(updatedCustomer.getAddress());

        customerRepository.update(existingCustomer);

        return existingCustomer;
    }

    // Delete a customer
    public void deleteCustomer(String customerId) {

        getCustomerById(customerId);

        customerRepository.deleteById(customerId);
    }

    // Validate customer details
    private void validateCustomer(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer information is required."
            );
        }

        if (isBlank(customer.getCustomerId())) {
            throw new IllegalArgumentException(
                    "Customer ID is required."
            );
        }

        if (isBlank(customer.getName())) {
            throw new IllegalArgumentException(
                    "Customer name is required."
            );
        }

        if (isBlank(customer.getEmail())) {
            throw new IllegalArgumentException(
                    "Customer email is required."
            );
        }

        if (!customer.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            throw new IllegalArgumentException(
                    "Please enter a valid email address."
            );
        }

        if (isBlank(customer.getPhone())) {
            throw new IllegalArgumentException(
                    "Customer phone number is required."
            );
        }

        if (isBlank(customer.getAddress())) {
            throw new IllegalArgumentException(
                    "Customer address is required."
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
