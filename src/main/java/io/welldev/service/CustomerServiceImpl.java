package io.welldev.service;

import io.welldev.dao.CustomerDao;
import io.welldev.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> getCustomers() {
        return this.customerDao.getCustomers();
    }


    @Override
    public Customer getCustomer(String username) {
        return this.customerDao.getCustomer(username);
    }

    @Override
    public void createCustomer(Customer customer) {
        System.out.println(customer);
        this.customerDao.createCustomer(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        System.out.println(customer);
        this.customerDao.updateCustomer(customer);
    }

    @Override
    public void patchCustomer(Customer customer){
        this.customerDao.patchCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        this.customerDao.deleteCustomer(customer);
    }

}
