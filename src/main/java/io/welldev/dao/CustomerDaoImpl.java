package io.welldev.dao;

import io.welldev.entity.Customer;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao{

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<Customer> getCustomers() {
        return this.hibernateTemplate.loadAll(Customer.class);
    }


    @Override
    public Customer getCustomer(String username) {
        return this.hibernateTemplate.get(Customer.class, username);
    }


    @Override
    @Transactional
    public void createCustomer(Customer customer) {
        this.hibernateTemplate.save(customer);
    }


    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        this.hibernateTemplate.update(customer);
    }

    @Override
    @Transactional
    public void patchCustomer(Customer customer) {
        this.hibernateTemplate.update(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Customer customer) {
        this.hibernateTemplate.delete(customer);
    }

}
