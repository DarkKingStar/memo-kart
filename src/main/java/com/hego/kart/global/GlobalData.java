package com.hego.kart.global;

import java.util.ArrayList;
import java.util.List;

import com.hego.kart.model.Product;

public class GlobalData {
    public static List<Product> cart;
    static{
        cart = new ArrayList<Product>();
    }
}
