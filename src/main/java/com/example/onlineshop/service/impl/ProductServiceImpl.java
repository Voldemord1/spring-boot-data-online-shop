package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductJpaRepository;
import com.example.onlineshop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productJpaRepository;

    @Autowired
    public ProductServiceImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    @Transactional
    public void addProduct(Product product) {
        productJpaRepository.save(product);
    }

    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return productJpaRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Product> getProductById(Long productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    @Transactional
    public void removeProduct(Product product) {
       productJpaRepository.delete(product);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        Optional<Product> productById = getProductById(product.getId());
        if(productById.isPresent()){
            productById.get().setName(product.getName());
            productById.get().setDescription(product.getDescription());
            productById.get().setPrice(product.getPrice());
        }
    }
}
