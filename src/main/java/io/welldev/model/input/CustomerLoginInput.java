package io.welldev.model.input;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerLoginInput {

    @NotEmpty(message = "should not be empty/null")
    @NotBlank(message = "should contain some character besides whitespace")
    private String username;

    @NotEmpty(message = "should not be empty")
    private String password;


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
}
