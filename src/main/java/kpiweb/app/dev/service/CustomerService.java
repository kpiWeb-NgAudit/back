package kpiweb.app.dev.service;

import kpiweb.app.dev.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Optional<Customer> getCustomerById(String cubeIdPk);
    List<Customer> getAllCustomers();
    Customer updateCustomer(String cubeIdPk, Customer customerDetails);
    void deleteCustomer(String cubeIdPk);
}