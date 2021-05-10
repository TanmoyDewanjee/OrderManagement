package io.welldev.controller;

import io.welldev.entity.Customer;
import io.welldev.exception.CustomerLoginException;
import io.welldev.exception.InvalidRequestBodyException;
import io.welldev.model.input.CustomerLoginInput;
import io.welldev.service.CustomerService;
import io.welldev.util.AuthorizationUtility;
import io.welldev.util.HashingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/session")
public class CustomerAuthenticationRestController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> customerLogin(@Valid @RequestBody CustomerLoginInput customerLoginInput,
                                              BindingResult bindingResult,
                                              HttpSession session){

        if (bindingResult.hasErrors()){
            throw new InvalidRequestBodyException(bindingResult);
        }

        if(AuthorizationUtility.getLoginToken(session) != null){
            throw new CustomerLoginException("please log out first");
        }

        Customer customer = this.customerService.getCustomer(customerLoginInput.getUsername());

        if (customer == null){
            return ResponseEntity.notFound().build();
        }

        if (customer.getPassword().equals(HashingUtility.getHashedString(customerLoginInput.getPassword()))){
            AuthorizationUtility.setLoginToken(session, customer);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> customerLogOut(HttpSession session){

        session.invalidate();

        return ResponseEntity.ok().build();
    }
}

