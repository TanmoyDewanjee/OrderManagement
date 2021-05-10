package io.welldev.model.input;

import io.welldev.entity.CustomerOrder;
import io.welldev.util.NullOrNotBlank;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


public class CustomerPatchInput {

    private String username;

    @NullOrNotBlank(message = "can not be empty")
    private String password;

    @Valid
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
