/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.service;

import com.pablinchapin.tiendaliz.catalogo.entity.Product;
import com.pablinchapin.tiendaliz.catalogo.exception.ResourceNotFoundException;
import com.pablinchapin.tiendaliz.catalogo.repository.ProductRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author pvargas
 */


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Page<Product> getAllProductsByCategoryId(Pageable pageable, Long categoryId) {
        return productRepository.findAllByCategoryId(pageable, categoryId);
    }

    @Override
    public Optional<Product> getProductDetail(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }
    
}
