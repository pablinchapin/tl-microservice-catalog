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
import com.pablinchapin.tiendaliz.catalogo.util.TlThreadLocalHolder;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;

/**
 *
 * @author pvargas
 */


@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    
    private final ProductRepository productRepository;
    private final InventoryServiceClient inventoryServiceClient;

    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, InventoryServiceClient inventoryServiceClient) {
        this.productRepository = productRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }
    
        
    /*
    @Autowired
    private RestTemplate restTemplate;
    */
    
    @Override
    public Page<Product> getAllProductsByCategoryId(Pageable pageable, Long categoryId) {
        return productRepository.findAllByCategoryId(pageable, categoryId);
    }

    @Override
    public Optional<Product> getProductDetail(Long id) {
        
        Optional<Product> product = productRepository.findById(id);
        
        if(product.isPresent()){
            
            String correlationId = UUID.randomUUID().toString();
            
            TlThreadLocalHolder.setCorrelationId(correlationId);
            
            log.info("Before correlationId: "+TlThreadLocalHolder.getCorrelationId());
            log.info("Fetching inventory level for productId: "+id);
            
            /*
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
            */
            
            Optional<ProductInventoryResponse> 
                    productInventoryResponse = inventoryServiceClient.getProductInventoryById(id);
            
            if(productInventoryResponse.isPresent()){
                Integer quantity = productInventoryResponse.get().getQuantity();
                product.get().setInStock(quantity > 0);
                log.info("Available quantity for productId: "+id+" / "+quantity);
            }
            
            log.info("After correlationId: "+TlThreadLocalHolder.getCorrelationId());
        
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
