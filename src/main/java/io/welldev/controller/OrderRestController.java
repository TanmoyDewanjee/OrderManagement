package io.welldev.controller;

import io.welldev.entity.CustomerOrder;
import io.welldev.exception.InvalidRequestBodyException;
import io.welldev.service.OrderService;
import io.welldev.util.AuthorizationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/customers/{username}/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getCustomerOrders(@PathVariable("username") String username){

        List<CustomerOrder> orders = this.orderService.getCustomerOrders(username);

        if (orders == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orders);
    }


    @GetMapping(path = "{orderId}")
    public ResponseEntity<CustomerOrder> getCustomerOrder(@PathVariable("username") String username, @PathVariable("orderId") int orderId){

        CustomerOrder order = this.orderService.getCustomerOrder(username, orderId);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }



    @PostMapping
    public ResponseEntity<CustomerOrder> createOrder(@PathVariable("username") String username,
                                                     @Valid @RequestBody CustomerOrder order,
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

        boolean orderCreated = this.orderService.createCustomerOrder(username, order);

        if (! orderCreated){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }


    @PatchMapping(path = "{orderId}")
    public ResponseEntity<CustomerOrder> updateOrder(@PathVariable("username") String username,
                                                     @PathVariable("orderId") int orderId,
                                                     @Valid @RequestBody CustomerOrder orderBody,
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


        CustomerOrder order = this.orderService.getCustomerOrder(username, orderId);

        if (order == null){
            return ResponseEntity.notFound().build();
        }

        orderBody.setId(orderId);
        this.orderService.updateCustomerOrder(username, orderBody);

        return ResponseEntity.ok().body(orderBody);
    }

    @Transactional
    @DeleteMapping(path = "{orderId}")
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable("username") String username,
                                                    @PathVariable("orderId") int orderId,
                                                    HttpSession session){

        if (! AuthorizationUtility.isCustomerLoggedIn(session)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (! AuthorizationUtility.isCustomerAuthorized(username, session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boolean isOrderDeleted = this.orderService.deleteCustomerOrder(username, orderId);

        if (! isOrderDeleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
