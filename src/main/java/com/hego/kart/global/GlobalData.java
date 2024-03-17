package com.hego.kart.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hego.kart.model.Product;
import com.hego.kart.model.User;

public class GlobalData {
    public static List<Product> cart;
    static{
        cart = new ArrayList<Product>();
    }
    public static Optional<User> currentUser;
}
