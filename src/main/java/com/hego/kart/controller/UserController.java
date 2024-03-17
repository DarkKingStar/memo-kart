package com.hego.kart.controller;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hego.kart.global.GlobalData;
import com.hego.kart.service.CategoryService;
import com.hego.kart.service.OfferService;
import com.hego.kart.service.ProductService;
import com.hego.kart.service.UserService;

@Controller
public class UserController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    OfferService offerService;
    @Autowired
    UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping({"/", "/home"})
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("offers", offerService.getAllOffers());
        Cookie[] cookies = request.getCookies();
        //get EMIAL from cookies
        String email = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("EMAIL")) {
                email = cookie.getValue();
            }
        }
        try{
            logger.info("user",cookies.toString());
        }catch(Exception e){
            logger.info("user not found");
        }
        return "index";
    }
    
    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProducts());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getProductsByCategoryId(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String shopByProduct(Model model, @PathVariable Long id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", productService.getProductById(id).get());
        return "viewProduct";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        //get EMIAL from cookies
        String email = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("EMAIL")) {
                email = cookie.getValue();
            }
        }
        try{
            model.addAttribute("user", userService.findUserByEmail(email).get());
        }catch(Exception e){
            logger.info("user not found");
        }
        return "profile";
    }


}
