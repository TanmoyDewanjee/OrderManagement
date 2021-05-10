package io.welldev.entity;

import io.welldev.util.HashingUtility;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @NotEmpty(message = "should not be empty/null")
    @NotBlank(message = "should contain some character besides whitespace")
    @Column(unique = true)
    @Id
    private String username;

    @NotEmpty(message = "should not be empty")
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CustomerOrder> orders = new ArrayList<>();

    private final LocalDate creationDate;


    public Customer(){
        this.creationDate = LocalDate.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
//        this.password = password;
        this.password = HashingUtility.getHashedString(password);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public List<CustomerOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", orders=" + orders +
                ", creationDate=" + creationDate +
                '}';
    }
}
