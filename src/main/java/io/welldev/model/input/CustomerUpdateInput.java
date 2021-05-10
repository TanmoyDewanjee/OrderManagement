package io.welldev.model.input;

import io.welldev.entity.CustomerOrder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CustomerUpdateInput {

    private String username;

    @NotEmpty(message = "should not be empty")
    private String password;

    @Valid
    @NotNull
    private List<CustomerOrder> orders;


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
        this.password = password;
    }

    public List<CustomerOrder> getOrders() {
        return orders;
    }



    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }
}
