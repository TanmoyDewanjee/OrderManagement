package io.welldev.service;

import io.welldev.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();
    Customer getCustomer(String username);
    void createCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void patchCustomer(Customer customer);
    void deleteCustomer(Customer customer);
}
