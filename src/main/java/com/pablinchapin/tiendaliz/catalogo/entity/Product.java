/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author pvargas
 */

@Data
@Entity
@Table(name = "products")
public class Product {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long categoryId;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false, length = 255)
    private String description;
    
    @Column(nullable = false, length = 255)
    private String nutritionalInfo;
    
    @Column(nullable = false, length = 255)
    private String brand;
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private double tax;
    
    @Column(nullable = false)
    private boolean active;
    
    
    private String frontImageUrl;
    private String frontThumbImageUrl;
    
}
