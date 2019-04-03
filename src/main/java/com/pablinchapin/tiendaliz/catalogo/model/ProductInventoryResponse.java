/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.model;

import lombok.Data;

/**
 *
 * @author pvargas
 */

@Data
public class ProductInventoryResponse {
    
    private Long productId;
    private Integer quantity = 0;
    
}
