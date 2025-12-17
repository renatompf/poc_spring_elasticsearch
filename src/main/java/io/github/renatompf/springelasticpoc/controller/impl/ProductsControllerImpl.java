package io.github.renatompf.springelasticpoc.controller.impl;

import io.github.renatompf.springelasticpoc.controller.ProductsController;
import io.github.renatompf.springelasticpoc.dto.ProductCreateDTO;
import io.github.renatompf.springelasticpoc.model.ProductDocument;
import io.github.renatompf.springelasticpoc.model.ProductEntity;
import io.github.renatompf.springelasticpoc.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsControllerImpl implements ProductsController {

    private final ProductService productService;

    public ProductsControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductCreateDTO product) {
        productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<ProductEntity>> getAll(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDocument>> getByNameAndDescription(@RequestParam String query, Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getByNameAndDescription(query, pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
