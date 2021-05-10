package io.welldev.model.factory;

import io.welldev.entity.Customer;
import io.welldev.model.input.CustomerRegistrationInput;
import io.welldev.model.input.CustomerUpdateInput;
import io.welldev.model.output.CustomerOutput;

import java.util.ArrayList;
import java.util.List;

public class CustomerFactory {

    public static Customer buildCustomerEntityFromCustomerInput(CustomerRegistrationInput customerRegistrationInput) {
        Customer customer = new Customer();
        customer.setUsername(customerRegistrationInput.getUsername());
        customer.setPassword(customerRegistrationInput.getPassword());
        customer.setOrders(customerRegistrationInput.getOrders());

        return customer;
    }

    public static Customer buildCustomerEntityFromCustomerPatchInput(CustomerUpdateInput customerInput) {
        Customer customer = new Customer();
        customer.setUsername(customerInput.getUsername());
        customer.setPassword(customerInput.getPassword());
        customer.setOrders(customerInput.getOrders());

        return customer;
    }

    public static Customer buildCustomerEntityFromCustomerUpdateInput(CustomerUpdateInput customerUpdateInput) {
        Customer customer = new Customer();
        customer.setUsername(customerUpdateInput.getUsername());
        customer.setPassword(customerUpdateInput.getPassword());
        customer.setOrders(customerUpdateInput.getOrders());

        return customer;
    }

    public static List<CustomerOutput> buildCustomerOutputFromCustomerEntity(List<Customer> customers){
        List<CustomerOutput> customerOutputs = new ArrayList<>();

        for (Customer customer: customers){
            customerOutputs.add(CustomerFactory.buildCustomerOutputFromCustomerEntity(customer));
        }

        return customerOutputs;
    }

    public static CustomerOutput buildCustomerOutputFromCustomerEntity(Customer customer) {
        CustomerOutput customerOutput = new CustomerOutput();
        customerOutput.setUsername(customer.getUsername());
        customerOutput.setCreationDate(customer.getCreationDate());
        customerOutput.setOrders(customer.getOrders());

        return customerOutput;
    }

}
