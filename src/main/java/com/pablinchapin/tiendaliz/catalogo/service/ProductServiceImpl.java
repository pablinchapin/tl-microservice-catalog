/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.service;

import com.pablinchapin.tiendaliz.catalogo.entity.Product;
import com.pablinchapin.tiendaliz.catalogo.exception.ResourceNotFoundException;
import com.pablinchapin.tiendaliz.catalogo.model.ProductInventoryResponse;
import com.pablinchapin.tiendaliz.catalogo.repository.ProductRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author pvargas
 */


@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public Page<Product> getAllProductsByCategoryId(Pageable pageable, Long categoryId) {
        return productRepository.findAllByCategoryId(pageable, categoryId);
    }

    @Override
    public Optional<Product> getProductDetail(Long id) {
        
        Optional<Product> product = productRepository.findById(id);
        
        if(product.isPresent()){
            
            log.info("Fetching inventory level for productId: "+id);
            
            ResponseEntity<ProductInventoryResponse> 
                    productResponse = restTemplate
                                        .getForEntity(
                                                "http://inventory-service/inventory/api/product/{id}",
                                                ProductInventoryResponse.class,
                                                id);

            if(productResponse.getStatusCode() == HttpStatus.OK){
            
                Integer quantity = productResponse.getBody().getQuantity();
                log.info("Available quantity for productId: "+id+" / "+quantity);
                product.get().setInStock(quantity > 0);
            }else{
                    log.error("Unable to get inventoty level for productId: "+id+ " StatusCode: "+productResponse.getStatusCode());
            }
            
        
        }
        
        
        return product;//productRepository.findById(id);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }
    
}
