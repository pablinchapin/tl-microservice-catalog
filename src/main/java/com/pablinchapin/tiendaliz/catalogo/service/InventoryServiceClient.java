/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pablinchapin.tiendaliz.catalogo.model.ProductInventoryResponse;
import com.pablinchapin.tiendaliz.catalogo.util.TlThreadLocalHolder;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
public class InventoryServiceClient {
    
    private final RestTemplate restTemplate;
    private static final String INVENTORY_API_URL = "http://inventory-service/inventory/api/";

    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    
    @HystrixCommand(fallbackMethod = "getDefaultProductInventoryById")
    public Optional<ProductInventoryResponse> getProductInventoryById(Long id){
        
        log.info("CorrelationID: "+TlThreadLocalHolder.getCorrelationId());
    
        ResponseEntity<ProductInventoryResponse>
                productInventoryResponse = restTemplate
                                            .getForEntity(INVENTORY_API_URL +"product/{id}",
                                                ProductInventoryResponse.class,
                                                id);
        
        /*
        //Simulate delay
        try{
            java.util.concurrent.TimeUnit.SECONDS.sleep(5);
        }catch(InterruptedException e){
                e.printStackTrace();
        }
        */
                
        if(productInventoryResponse.getStatusCode() == HttpStatus.OK){
            log.info("Available quantity: "+productInventoryResponse.getBody().toString());
            return Optional.ofNullable(productInventoryResponse.getBody());
        }else{
                log.error("Unable to get inventory for productId: " +id);
                return Optional.empty();
        }
        
    }
    
    
    @SuppressWarnings("unused")
    Optional<ProductInventoryResponse> getDefaultProductInventoryById(Long id){
        
        log.info("Returning default product inventory for productId: "+id);
        
        ProductInventoryResponse productInventoryResponse = new ProductInventoryResponse();
        productInventoryResponse.setProductId(id);
        productInventoryResponse.setQuantity(5);
        
            
    return Optional.ofNullable(productInventoryResponse);
    }
    
    
    
}
