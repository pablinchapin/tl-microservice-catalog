/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.controller;

import com.pablinchapin.tiendaliz.catalogo.entity.Category;
import com.pablinchapin.tiendaliz.catalogo.entity.Product;
import com.pablinchapin.tiendaliz.catalogo.service.CategoryServiceImpl;
import com.pablinchapin.tiendaliz.catalogo.service.ProductServiceImpl;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pvargas
 */

@RestController
@RequestMapping("/api/catalog")
@Slf4j
public class CatalogController {
    
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);
    
    private final CategoryServiceImpl categoryService;
    private final ProductServiceImpl productService;
    
    
    @Autowired
    public CatalogController(CategoryServiceImpl categoryService, ProductServiceImpl productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }
    
    
    @GetMapping("/type")
    public Page<Category> typePage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        PageRequest pageable = PageRequest.of(page -1, size);
        Page<Category> categoryPage = categoryService.getAllCategories(pageable);
        
    return categoryPage;        
    }
    
    
    @GetMapping("/productList")
    public Page<Product> productPage(
            @RequestParam(value = "typeId", defaultValue = "1", required = false) Long typeId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        
        PageRequest pageable = PageRequest.of(page -1, size);
        Page<Product> productPage = productService.getAllProductsByCategoryId(pageable, typeId);
    
    return productPage;
    }
    
    
    @GetMapping("productDetail")
    public Product productDetail(
            HttpServletRequest request,
            @RequestParam(value = "id", defaultValue = "") Long id
    ){
        
        Optional<Product> product;
        Product productData = new Product();
        product = productService.getProductDetail(id);
        
        if(product.isPresent()){
            productData = product.get();
        }
        
        logger.info(productData.toString());
    
    return productData;
    }
    
    
    
}
