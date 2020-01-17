package com.codegym.controllers;

import com.codegym.models.OrderDetail;
import com.codegym.models.Promotion;
import com.codegym.services.OrderDetailService;
import com.codegym.services.PromotionService;
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
@RequestMapping("/api/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("")
    public ResponseEntity<List<Promotion>> listAllPromotion() {
        List<Promotion> promotions = (List<Promotion>) promotionService.findAll();
        if (promotions.isEmpty()) {
            return new ResponseEntity<List<Promotion>>(promotions, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Promotion>>(promotions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Promotion>> getPromotion(@PathVariable("id") long id) {
        System.out.println("Promotion with id " + id);
        Optional<Promotion> promotion = promotionService.findById(id);
        if (!promotion.isPresent()) {
            System.out.println("Promotion with id " + id + "not found");
            return new ResponseEntity<Optional<Promotion>>(promotion, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Optional<Promotion>>(promotion, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createPromotion(@RequestBody Promotion promotion, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Create promotion" + promotion.getName());
        promotionService.save(promotion);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/promotion/{id}").buildAndExpand(promotion.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Promotion>> updatePromotion(@PathVariable("id") long id, @RequestBody Promotion promotion) {
        System.out.println("Update promotion " + id);

        Optional<Promotion> currentPromotion = promotionService.findById(id);

        if (!currentPromotion.isPresent()) {
            System.out.println("promotion with id" + id + "not found");
            return new ResponseEntity<Optional<Promotion>>(HttpStatus.NOT_FOUND);
        }

        currentPromotion.get().setName(promotion.getName());
        currentPromotion.get().setPrice(promotion.getPrice());
        currentPromotion.get().setProducts(promotion.getProducts());
        promotionService.save(promotion);
        return new ResponseEntity<Optional<Promotion>>(currentPromotion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Promotion> deletePromotion(@PathVariable("id") long id) {
        System.out.println("Delete promotion with id" + id);

        Optional<Promotion> promotion = promotionService.findById(id);
        if (!promotion.isPresent()) {
            System.out.println("Unable to delete. Promotion with id" + id + "not found");
            return new ResponseEntity<Promotion>(HttpStatus.NOT_FOUND);
        }
        promotionService.remove(id);
        return new ResponseEntity<Promotion>(HttpStatus.NO_CONTENT);
    }
}
