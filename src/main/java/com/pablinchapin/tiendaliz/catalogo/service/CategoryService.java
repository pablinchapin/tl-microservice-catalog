/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.service;

import com.pablinchapin.tiendaliz.catalogo.entity.Category;
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
public interface CategoryService {
    
    @NotNull Page<Category> getAllCategories(Pageable pageable);
    Category getCategory(@Min(value = 1L, message = "Invalid category ID.") Long id);
    
    Category save(Category category);
    
}
