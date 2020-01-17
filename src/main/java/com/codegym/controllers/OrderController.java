package com.codegym.controllers;
import com.codegym.models.Order;
import com.codegym.models.Payment;
import com.codegym.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<Order>> listAllOrders() {
        List<Order> orders = (List<Order>) orderService.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<List<Order>>(orders, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrder(@PathVariable("id") long id) {
        System.out.println("Order with id " + id);
        Optional<Order> order = orderService.findById(id);
        if (!order.isPresent()) {
            System.out.println("Order with id " + id + "not found");
            return new ResponseEntity<Optional<Order>>(order, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Optional<Order>>(order, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createOrderg(@RequestBody Order order, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Create order" + order.getId());
        orderService.save(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/order/{id}").buildAndExpand(order.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Order>> updateOrder(@PathVariable("id") long id, @RequestBody Order order) {
        System.out.println("Update order" + id);

        Optional<Order> currentOrder = orderService.findById(id);

        if (!currentOrder.isPresent()) {
            System.out.println("Order with id" + id + "not found");
            return new ResponseEntity<Optional<Order>>(HttpStatus.NOT_FOUND);
        }

        currentOrder.get().setPurchaseDate(order.getPurchaseDate());
        currentOrder.get().setDeliveryDate(order.getDeliveryDate());
        currentOrder.get().setDescription(order.getDescription());
        currentOrder.get().setOrderDetails(order.getOrderDetails());
        currentOrder.get().setPayment(order.getPayment());
        orderService.save(order);
        return new ResponseEntity<Optional<Order>>(currentOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") long id) {
        System.out.println("Delete order with id" + id);

        Optional<Order> order = orderService.findById(id);
        if (!order.isPresent()) {
            System.out.println("Unable to delete. Order with id" + id + "not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        orderService.remove(id);
        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }
}
