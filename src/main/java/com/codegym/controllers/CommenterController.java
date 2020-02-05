package com.codegym.controllers;

import com.codegym.models.Commenter;
import com.codegym.models.Product;
import com.codegym.services.CommenterService;
import com.codegym.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/commenter")
public class CommenterController {
    @Autowired
    private CommenterService commenterService;
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public ResponseEntity<?> listCommenter(){
        List<Commenter> commenters = (List<Commenter>) commenterService.findAll();
        if (commenters.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commenters, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommenter(@PathVariable Long id){
        Optional<Commenter> commenter = commenterService.findById(id);
        if (!commenter.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commenter, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<?> createCommenter(@Valid @RequestBody Commenter commenter){
        if(commenter.getId() != null) {
            Optional<Product> product = productService.findById(commenter.getId());
            commenter.setProduct(product.get());
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = now.format(format);
        commenter.setEdit(false);
        commenter.setDate(date);

        commenterService.save(commenter);

        return new ResponseEntity<>(commenter, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommenter(@Valid @RequestBody Commenter commenter, @PathVariable Long id){
        Optional<Commenter> commenter1 = commenterService.findById(id);
        if(!commenter1.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = now.format(format);

        commenter1.get().setEdit(true);
        commenter1.get().setDate(date);
        commenter1.get().setContent(commenter.getContent());

        commenterService.save(commenter1.get());
        return new ResponseEntity<>(commenter1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommenter(@PathVariable Long id){
        Optional<Commenter> commenter = commenterService.findById(id);
        if (!commenter.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commenterService.delete(id);
        return new ResponseEntity<>(commenter, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getAllCommentByProductId(@PathVariable Long id) {
        List<Commenter> commenters = (List<Commenter>) commenterService.findCommentersByProductId(id);

        if(commenters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(commenters, HttpStatus.OK);
    }
}
