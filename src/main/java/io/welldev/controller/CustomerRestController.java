package io.welldev.controller;

import io.welldev.entity.Customer;
import io.welldev.exception.InvalidRequestBodyException;
import io.welldev.model.factory.CustomerFactory;
import io.welldev.model.input.CustomerPatchInput;
import io.welldev.model.input.CustomerRegistrationInput;
import io.welldev.model.input.CustomerUpdateInput;
import io.welldev.model.output.CustomerOutput;
import io.welldev.service.CustomerService;
import io.welldev.util.AuthorizationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerOutput>> getCustomers(){

        List<Customer> customers = this.customerService.getCustomers();

        List<CustomerOutput> customerOutputs = CustomerFactory.buildCustomerOutputFromCustomerEntity(customers);

        return ResponseEntity.ok().body(customerOutputs);
    }


    @GetMapping(path = "{username}")
    public ResponseEntity<CustomerOutput> getCustomer(@PathVariable("username") String username){
        Customer customer = this.customerService.getCustomer(username);

        if (customer == null){
            return ResponseEntity.notFound().build();
        }

        CustomerOutput customerOutput = CustomerFactory.buildCustomerOutputFromCustomerEntity(customer);

        return ResponseEntity.ok().body(customerOutput);
    }


    @PostMapping
    public ResponseEntity<CustomerOutput> createCustomer(@Valid @RequestBody CustomerRegistrationInput customerRegistrationInput,
                                                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new InvalidRequestBodyException(bindingResult);
        }

        Customer customer = CustomerFactory.buildCustomerEntityFromCustomerInput(customerRegistrationInput);
        this.customerService.createCustomer(customer);

        CustomerOutput customerOutput = CustomerFactory.buildCustomerOutputFromCustomerEntity(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerOutput);
    }


    @PutMapping(path = "{username}")
    public ResponseEntity<CustomerOutput> updateCustomer(@PathVariable("username") String username,
                                                         @Valid @RequestBody CustomerUpdateInput customerUpdateInput,
                                                         BindingResult bindingResult,
                                                         HttpSession session){

        if (bindingResult.hasErrors()){
            throw new InvalidRequestBodyException(bindingResult);
        }


        if (! AuthorizationUtility.isCustomerLoggedIn(session)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (! AuthorizationUtility.isCustomerAuthorized(username, session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        Customer customer = this.customerService.getCustomer(username);

        if (customer == null){
            customerUpdateInput.setUsername(username);
            customer = CustomerFactory.buildCustomerEntityFromCustomerUpdateInput(customerUpdateInput);
            this.customerService.createCustomer(customer);

            CustomerOutput customerOutput = CustomerFactory.buildCustomerOutputFromCustomerEntity(customer);

            return ResponseEntity.status(HttpStatus.CREATED).body(customerOutput);
        }


        customer.setPassword(customerUpdateInput.getPassword());
        customer.setOrders(customerUpdateInput.getOrders());

        this.customerService.updateCustomer(customer);

        CustomerOutput customerOutput = CustomerFactory.buildCustomerOutputFromCustomerEntity(customer);

        return ResponseEntity.ok().body(customerOutput);
    }

    @PatchMapping(path = "{username}")
    public ResponseEntity<CustomerOutput> patchCustomer(@PathVariable("username") String username,
                                                        @Valid @RequestBody CustomerPatchInput customerPatchInput,
                                                        BindingResult bindingResult,
                                                        HttpSession session){

        if (bindingResult.hasErrors()){
            throw new InvalidRequestBodyException(bindingResult);
        }

        if (! AuthorizationUtility.isCustomerLoggedIn(session)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (! AuthorizationUtility.isCustomerAuthorized(username, session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Customer customer = this.customerService.getCustomer(username);

        if (customer == null){
            return ResponseEntity.notFound().build();
        }

        if (customerPatchInput.getPassword() != null){
            customer.setPassword(customerPatchInput.getPassword());
        }

        if (customerPatchInput.getOrders() != null){
            customer.setOrders(customerPatchInput.getOrders());
        }

        this.customerService.patchCustomer(customer);

        CustomerOutput customerOutput = CustomerFactory.buildCustomerOutputFromCustomerEntity(customer);

        return ResponseEntity.ok().body(customerOutput);
    }


    @DeleteMapping(path = "{username}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("username") String username,
                                               HttpSession session){

        if (! AuthorizationUtility.isCustomerLoggedIn(session)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (! AuthorizationUtility.isCustomerAuthorized(username, session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Customer customer = this.customerService.getCustomer(username);

        if (customer == null){
            return ResponseEntity.notFound().build();
        }

        this.customerService.deleteCustomer(customer);
        session.invalidate();

        return ResponseEntity.noContent().build();
    }

}
