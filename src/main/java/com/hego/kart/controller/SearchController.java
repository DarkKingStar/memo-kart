package com.hego.kart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hego.kart.model.Product;
import com.hego.kart.service.ProductService;

@RestController
public class SearchController {
    @Autowired
    ProductService productService;

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String query){
        List<Product> suggestions  = productService.searchProducts(query);
        return suggestions;
    }
}
