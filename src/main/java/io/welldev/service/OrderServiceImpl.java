package io.welldev.service;

import io.welldev.dao.CustomerDao;
import io.welldev.dao.OrderDao;
import io.welldev.entity.Customer;
import io.welldev.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    public List<CustomerOrder> getCustomerOrders(String username){
        return this.orderDao.getCustomerOrders(username);
    }

    public CustomerOrder getCustomerOrder(String username, int orderId){
        return this.orderDao.getCustomerOrder(username, orderId);
    }


    public boolean createCustomerOrder(String username, CustomerOrder order){
        return this.orderDao.createCustomerOrder(username, order);
    }

    public boolean updateCustomerOrder(String username, CustomerOrder order){
        return this.orderDao.updateCustomerOrder(username, order);
    }

    public boolean deleteCustomerOrder(String username, int orderId){
        return this.orderDao.deleteCustomerOrder(username, orderId);
    }
}
