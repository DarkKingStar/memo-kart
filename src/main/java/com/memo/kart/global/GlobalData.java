package com.memo.kart.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.memo.kart.model.Product;
import com.memo.kart.model.User;

public class GlobalData {
    public static List<Product> cart;
    static{
        cart = new ArrayList<Product>();
    }
    public static Optional<User> currentUser;
}
