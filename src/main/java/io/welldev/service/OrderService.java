package io.welldev.service;

import io.welldev.entity.CustomerOrder;

import java.util.List;

public interface OrderService {

    List<CustomerOrder> getCustomerOrders(String username);
    CustomerOrder getCustomerOrder(String username, int orderId);
    boolean createCustomerOrder(String username, CustomerOrder order);
    boolean updateCustomerOrder(String username, CustomerOrder order);
    boolean deleteCustomerOrder(String username, int orderId);
}
