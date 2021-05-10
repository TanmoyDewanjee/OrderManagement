package io.welldev.dao;

import io.welldev.entity.Customer;
import io.welldev.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao{

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<CustomerOrder> getCustomerOrders(String username){
        Customer customer = this.customerDao.getCustomer(username);

        if (customer == null){
            return null;
        }

        return customer.getOrders();
    }

    @Override
    public CustomerOrder getCustomerOrder(String username, int orderId){
        Customer customer = this.customerDao.getCustomer(username);

        if (customer == null) {
            return null;
        }

        List<CustomerOrder> orders = customer.getOrders();
        CustomerOrder order = null;

        for (CustomerOrder customerOrder: orders){
            if (customerOrder.getId() == orderId){
                order = customerOrder;
                break;
            }
        }

        return order;
    }

    @Override
    @Transactional
    public boolean createCustomerOrder(String username, CustomerOrder order){
        Customer customer = this.customerDao.getCustomer(username);

        if (customer == null) {
            return false;
        }

        customer.getOrders().add(order);
        this.hibernateTemplate.update(customer);

        return true;
    }

    @Override
    @Transactional
    public boolean updateCustomerOrder(String username, CustomerOrder order){
        Customer customer = this.customerDao.getCustomer(username);

        if (customer == null){
            return false;
        }

        List<CustomerOrder> orders = customer.getOrders();
        CustomerOrder orderToUpdate = null;

        for (CustomerOrder customerOrder: orders){
            if (customerOrder.getId() == order.getId()){
                orderToUpdate = customerOrder;
                break;
            }
        }

        if (orderToUpdate == null){
            return false;
        }

        orderToUpdate.setDescription(order.getDescription());
        this.hibernateTemplate.update(customer);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteCustomerOrder(String username, int orderId){
        Customer customer = this.customerDao.getCustomer(username);

        if (customer == null){
            return false;
        }

        List<CustomerOrder> orders = customer.getOrders();
        CustomerOrder orderToDelete = null;

        for (CustomerOrder customerOrder: orders){
            if (customerOrder.getId() == orderId){
                orderToDelete = customerOrder;
                break;
            }
        }

        if (orderToDelete == null){
            return false;
        }

        this.hibernateTemplate.delete(orderToDelete);
        orders.remove(orderToDelete);

        return true;
    }
}
