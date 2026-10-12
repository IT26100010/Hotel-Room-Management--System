package com.hotel.repository;

import com.hotel.model.Customer;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {

    private final Path filePath =
            Path.of("data", "customers.txt");

    // Create the data directory and file if necessary
    public CustomerRepository() {

        try {
            Files.createDirectories(filePath.getParent());

            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not initialize customer file.", e);
        }
    }

    // Read all customers from the text file
    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split("\\|", -1);

                if (data.length != 5) {
                    throw new IllegalStateException(
                            "Invalid customer record in customers.txt");
                }

                Customer customer = new Customer(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );

                customers.add(customer);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not read customer file.", e);
        }

        return customers;
    }

    // Find a customer by ID
    public Optional<Customer> findById(String customerId) {

        return findAll()
                .stream()
                .filter(customer ->
                        customer.getCustomerId()
                                .equalsIgnoreCase(customerId))
                .findFirst();
    }

    // Save a new customer
    public Customer save(Customer customer) {

        List<Customer> customers = findAll();

        customers.add(customer);

        writeAll(customers);

        return customer;
    }

    // Update an existing customer
    public boolean update(Customer updatedCustomer) {

        List<Customer> customers = findAll();

        for (int i = 0; i < customers.size(); i++) {

            if (customers.get(i).getCustomerId()
                    .equalsIgnoreCase(
                            updatedCustomer.getCustomerId())) {

                customers.set(i, updatedCustomer);

                writeAll(customers);

                return true;
            }
        }

        return false;
    }

    // Delete a customer
    public boolean deleteById(String customerId) {

        List<Customer> customers = findAll();

        boolean removed = customers.removeIf(
                customer -> customer.getCustomerId()
                        .equalsIgnoreCase(customerId)
        );

        if (removed) {
            writeAll(customers);
        }

        return removed;
    }

    // Write all customer records to the text file
    private void writeAll(List<Customer> customers) {

        List<String> lines = new ArrayList<>();

        for (Customer customer : customers) {

            String line =
                    customer.getCustomerId() + "|" +
                            customer.getName() + "|" +
                            customer.getEmail() + "|" +
                            customer.getPhone() + "|" +
                            customer.getAddress();

            lines.add(line);
        }

        try {
            Files.write(
                    filePath,
                    lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not write customer file.", e);
        }
    }
}
