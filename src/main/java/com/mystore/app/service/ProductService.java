package com.mystore.app.service;

import com.mystore.app.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final List<Product> productList = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    // Create a product
    public Product addProduct(Product product) {
        product.setId(idGenerator.getAndIncrement());
        productList.add(product);
        return product;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productList;
    }

    // Get product by ID
    public Optional<Product> getProductById(int id) {
        return productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    // Update product
    public Optional<Product> updateProduct(int id, Product updatedProduct) {
        Optional<Product> optionalProduct = getProductById(id);
        optionalProduct.ifPresent(product -> {
            product.setName(updatedProduct.getName());
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());
            product.setStockQuantity(updatedProduct.getStockQuantity());
        });
        return optionalProduct;
    }

    // Delete product
    public boolean deleteProduct(int id) {
        return productList.removeIf(p -> p.getId() == id);
    }

    // Search by name (case-insensitive)
    public List<Product> searchByName(String name) {
        return productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Filter by category (case-insensitive)
    public List<Product> filterByCategory(String category) {
        return productList.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Filter by price range
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        return productList.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // Filter by stock quantity range
    public List<Product> filterByStockRange(int minStock, int maxStock) {
        return productList.stream()
                .filter(p -> p.getStockQuantity() >= minStock && p.getStockQuantity() <= maxStock)
                .collect(Collectors.toList());
    }
}
