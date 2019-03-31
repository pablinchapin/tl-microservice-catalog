/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.service;

import com.pablinchapin.tiendaliz.catalogo.entity.Product;
import java.util.Optional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author pvargas
 */

@Validated
public interface ProductService {
    
    @NotNull Page<Product> getAllProductsByCategoryId(Pageable pageable, Long categoryId);
    
    Optional<Product> getProductDetail(Long id);
    
    Product getProduct(@Min(value = 1L, message= "Invalid product ID") Long id);
    
    
}
