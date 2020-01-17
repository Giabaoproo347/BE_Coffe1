package com.codegym.controllers;

import com.codegym.models.OrderDetail;
import com.codegym.models.Promotion;
import com.codegym.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/orderDetail")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("")
    public ResponseEntity<Iterable<OrderDetail>> showListOrderDetail() {
        Iterable<OrderDetail> orderDetails = orderDetailService.findAll();
        return new ResponseEntity<Iterable<OrderDetail>>(orderDetails, HttpStatus.OK);
    }
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewOrderDetail(@Valid @RequestBody OrderDetail orderDetail){
        try {
            orderDetailService.save(orderDetail);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id){
        Optional<OrderDetail> orderDetail = orderDetailService.findById(id);
        if (orderDetail.isPresent()){
            System.out.println("find order detail");
            return new ResponseEntity<OrderDetail>(orderDetail.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail orderDetail){
        Optional<OrderDetail> currentOrderDetail = orderDetailService.findById(id);
        if (currentOrderDetail.isPresent()){
            currentOrderDetail.get().setId(id);
            currentOrderDetail.get().setSalePrice(orderDetail.getSalePrice());
            currentOrderDetail.get().setQuantity(orderDetail.getQuantity());
            currentOrderDetail.get().setOrder(orderDetail.getOrder());

            orderDetailService.save(currentOrderDetail.get());
            return new ResponseEntity<OrderDetail>(orderDetail, HttpStatus.OK);
        }
        return new ResponseEntity<OrderDetail>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDetail> deleteOrderDetail(@PathVariable Long id){
        Optional<OrderDetail> orderDetail = orderDetailService.findById(id);
        if (orderDetail.isPresent()){
            orderDetailService.remove(id);
            return new ResponseEntity<OrderDetail>(HttpStatus.OK);
        }
        return new ResponseEntity<OrderDetail>(HttpStatus.NOT_FOUND);
    }
}
