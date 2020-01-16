package com.codegym.controllers;

import com.codegym.models.ProductPicture;
import com.codegym.services.ProductPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/product-picture")
public class ProductPictureController {
    @Autowired
    ProductPictureService productPictureService;

    @GetMapping("")
    public ResponseEntity<Iterable<ProductPicture>> showListProductPicture() {
        Iterable<ProductPicture> productPictures = productPictureService.findAll();
        return new ResponseEntity<Iterable<ProductPicture>>(productPictures, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addNewProductPicture(@RequestBody ProductPicture productPicture) {
        System.out.println("Creating product picture ");
        productPictureService.save(productPicture);
        return new ResponseEntity<Long>(productPicture.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductPicture> getProductPictureById(@PathVariable Long id) {

        Optional<ProductPicture> productPicture = productPictureService.findById(id);
        if (productPicture.isPresent()) {
            return new ResponseEntity<ProductPicture>(productPicture.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductPicture> updateProductPicture(@PathVariable Long id, @RequestBody ProductPicture productPicture) {
        Optional<ProductPicture> currentProductPicture = productPictureService.findById(id);
        if (currentProductPicture.isPresent()) {
            currentProductPicture.get().setId(id);
            currentProductPicture.get().setSrc(productPicture.getSrc());

            productPictureService.save(currentProductPicture.get());
            return new ResponseEntity<ProductPicture>(currentProductPicture.get(), HttpStatus.OK);
        }
        return new ResponseEntity<ProductPicture>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductPicture> deleteProductPicture(@PathVariable Long id) {
        Optional<ProductPicture> productPicture = productPictureService.findById(id);
        if (productPicture.isPresent()) {
            productPictureService.remove(id);
            return new ResponseEntity<ProductPicture>(HttpStatus.OK);
        }
        return new ResponseEntity<ProductPicture>(HttpStatus.NOT_FOUND);
    }
}
