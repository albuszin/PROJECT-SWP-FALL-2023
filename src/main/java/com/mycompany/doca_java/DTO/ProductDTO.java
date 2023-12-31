/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doca_java.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Admin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO implements Serializable{

    private int productId;
    private int userId;
    private int categoryId;
    private String title;
    private String description;
    private String productImage;
    private boolean isFree;
    private float price;
    private String address;
    private Timestamp timePosted;
    private boolean isPublic;
    private String status;
    private String reason; 

    public ProductDTO(int userId, int categoryId, String title, String description, String productImage, boolean isFree, float price, String address, Timestamp timePosted, boolean isPublic, String status, String reason) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.productImage = productImage;
        this.isFree = isFree;
        this.price = price;
        this.address = address;
        this.timePosted = timePosted;
        this.isPublic = isPublic;
        this.status = status;
        this.reason = reason;
    }
   
    
}
