package com.codegym.controllers;

import com.codegym.models.Product;
import com.codegym.models.Category;
import com.codegym.models.Promotion;
import com.codegym.models.search.SearchProductByName;
import com.codegym.repositories.ProductRepository;
import com.codegym.services.CategoryService;
import com.codegym.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    Environment env;

    @GetMapping("")
    public ResponseEntity<List<Product>> listAllProduct() {
        List<Product> products = (List<Product>) productService.findAllBook();
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/top-hot")
    public ResponseEntity<Page<Product>> orderByLike(@PageableDefault(sort = {"like"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> products = (Page<Product>) productService.findAll(pageable);
        if (products.isEmpty()) {
            return new ResponseEntity<Page<Product>>(products, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProduct(@PathVariable("id") long id) {
        System.out.println("Fetching Product with id " + id);
        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()) {
            System.out.println("Product with id " + id + " not found");
            return new ResponseEntity<Optional<Product>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
    }



    @GetMapping("/categories/{id}")
    public ResponseEntity<List<Product>> listAllProductByCategories(@PathVariable Long id) {
        List<Product> products = productService.findByCategoryId(id);
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/promotion/{id}")
    public ResponseEntity<List<Product>> listAllProductByPromotion(@PathVariable Long id) {
        List<Product> products = productService.findByPromotionId(id);
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }




    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Product>> createProduct(@RequestBody Product product) {
        System.out.println("Creating Product " + product.getName());
        Category category = product.getCategory();
        Promotion promotion = product.getPromotion();
        Product currentProduct = new Product(product.getName(), product.getPrice(), product.getDescription(), product.getImage(), product.getImage2(), product.getImage3(), promotion, category, product.getLike());
        productService.save(currentProduct);
        return new ResponseEntity<Optional<Product>>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Product>> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        System.out.println("Updating Product " + id);

        Optional<Product> currentProduct = productService.findById(id);

        if (!currentProduct.isPresent()) {
            System.out.println("Product with id " + id + " not found");
            return new ResponseEntity<Optional<Product>>(HttpStatus.NOT_FOUND);
        }
        currentProduct.get().setName(product.getName());
        currentProduct.get().setPrice(product.getPrice());
        currentProduct.get().setDescription(product.getDescription());
        currentProduct.get().setImage(product.getImage());
        currentProduct.get().setImage2(product.getImage2());
        currentProduct.get().setImage3(product.getImage3());
        currentProduct.get().setPromotion(product.getPromotion());
        currentProduct.get().setCategory(product.getCategory());
        productService.save(currentProduct.get());
        return new ResponseEntity<Optional<Product>>(currentProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Product with id " + id);

        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()) {
            System.out.println("Unable to delete. Product with id " + id + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        productService.remove(id);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search-product-by-name")
    public ResponseEntity<?> findProductByName(@RequestBody SearchProductByName searchProductByName) {
        if(searchProductByName.getNameProduct() == "" || searchProductByName.getNameProduct() == null) {
            List<Product> products = (List<Product>) productService.findAllBook();
            if(products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products,HttpStatus.OK);
        } else {
            List<Product> products = (List<Product>) productService.findProductsByNameContaining(searchProductByName.getNameProduct());
            if(products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
    }

//    @PutMapping("/like/{id}")
//    public ResponseEntity<Optional<Product>> addLike(@PathVariable("id") long id, @RequestBody Product product) {
//        Optional<Product> currentProduct = productService.findById(id);
//
//        if (!currentProduct.isPresent()) {
//            return new ResponseEntity<Optional<Product>>(HttpStatus.NOT_FOUND);
//        }
//        currentProduct.get().setLike(product.getLike() + 1);
//        productService.save(currentProduct.get());
//        return new ResponseEntity<Optional<Product>>(currentProduct, HttpStatus.OK);
//    }

}