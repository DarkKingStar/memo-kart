package com.memo.kart.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.memo.kart.dto.ProductDTO;
import com.memo.kart.model.Category;
import com.memo.kart.model.Offer;
import com.memo.kart.model.Product;
import com.memo.kart.service.CategoryService;
import com.memo.kart.service.OfferService;
import com.memo.kart.service.ProductService;

@Controller
public class AdminController {
    public static String uplaodDirforproduct = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    public static String uplaodDirforcategory = System.getProperty("user.dir") + "/src/main/resources/static/categoryImages";
    public static String uplaodDirforoffer = System.getProperty("user.dir") + "/src/main/resources/static/offerImages";

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    OfferService offerService;

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    //Categories section
    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category,
    @RequestParam("categoryImage") MultipartFile file,
    @RequestParam("imgName") String imgName) throws IOException{
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath  = Paths.get(uplaodDirforcategory, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imageUUID = imgName;
        }
        category.setImageName(imageUUID);
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        List<Product> product = productService.getProductsByCategoryId(id);
        product.forEach(product1 -> productService.removeProductById(product1.getId()));
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        model.addAttribute("category", category.get());
        return "categoriesAdd";        
    }

    //Products Section
    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }
    
    @PostMapping("/admin/products/add")
    public String postProductsAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
    @RequestParam("productImage") MultipartFile file,
    @RequestParam("imgName") String imgName) throws IOException{ 
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath  = Paths.get(uplaodDirforproduct, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);

        productService.addProduct(product);
        
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model){
        Product  product  = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);

        return "productsAdd";
    }

    //Offers Section
    @GetMapping("/admin/offer")
    public String getOffer(Model model){
        model.addAttribute("offers", offerService.getAllOffers());
        return "offer";
    }
    @GetMapping("/admin/offer/add")
    public String getofferAdd(Model model){
        model.addAttribute("offer", new Offer());
        return "offerAdd";
    }

    @PostMapping("/admin/offer/add")
    public String postOfferAdd(@ModelAttribute("offer") Offer offer,
    @RequestParam("offerImage") MultipartFile file,
    @RequestParam("imgName") String imgName) throws IOException{
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath  = Paths.get(uplaodDirforoffer, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imageUUID = imgName;
        }
        offer.setBanner(imageUUID);
        offerService.addOffer(offer);
        return "redirect:/admin/offer";
    }

    @GetMapping("/admin/offer/delete/{id}")
    public String deleteOffer(@PathVariable int id){
        offerService.deleteOffer(id);
        return "redirect:/admin/offer";
    }

    @GetMapping("/admin/offer/update/{id}")
    public String updateOffer(@PathVariable int id, Model model){
        Optional<Offer> offer = offerService.getOffersbyId(id);
        model.addAttribute("offer", offer.get());
        return "offerAdd";        
    }


}

