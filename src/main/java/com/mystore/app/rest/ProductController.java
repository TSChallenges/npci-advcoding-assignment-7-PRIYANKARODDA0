package com.mystore.app.rest;

import com.mystore.app.entity.Product;
import com.mystore.app.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Optional<Product> updated = productService.updateProduct(id, product);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Search by name
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    // Filter by category
    @GetMapping("/filter/category")
    public ResponseEntity<List<Product>> filterByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.filterByCategory(category));
    }

    // Filter by price range
    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> filterByPrice(@RequestParam double minPrice,
                                                       @RequestParam double maxPrice) {
        return ResponseEntity.ok(productService.filterByPriceRange(minPrice, maxPrice));
    }

    // Filter by stock quantity range
    @GetMapping("/filter/stock")
    public ResponseEntity<List<Product>> filterByStock(@RequestParam int minStock,
                                                       @RequestParam int maxStock) {
        return ResponseEntity.ok(productService.filterByStockRange(minStock, maxStock));
    }
}
