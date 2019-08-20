package com.example.onlineshop.controller;

import com.example.onlineshop.model.Basket;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.User;
import com.example.onlineshop.service.interfaces.BasketService;
import com.example.onlineshop.service.interfaces.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@SessionAttributes("user")
public class ProductController {

    private ProductService productService;
    private BasketService basketService;
    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService, BasketService basketService) {
        this.productService = productService;
        this.basketService = basketService;
    }

    @GetMapping(path = {"/user/products", "/admin/products"})
    public String doGetAllProducts(
            HttpServletRequest request,
            @AuthenticationPrincipal User user) {
        List<Product> allProducts = productService.getAllProducts();
        request.setAttribute("products", allProducts);
        request.setAttribute("user", user);
        Optional<Basket> basketByUserId = basketService.getBasketByUserId(user.getId());
        if (basketByUserId.isPresent()) {
            int productCountOfBasket = basketByUserId.get().getProductList().size();
            request.setAttribute("productCountOfBasket", productCountOfBasket);
        }
        return "products";
    }

    @GetMapping("/admin/product")
    public String doGetEditProduct(Model model, @RequestParam("id") String productId) {
        if (Objects.nonNull(productId)) {
            Optional<Product> product = productService.getProductById(Long.parseLong(productId));
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
            }
        }
        return "edit_product";
    }

    @PostMapping("/admin/product")
    public String editProduct(
            Model model,
            @RequestParam("id") String productId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price) {
        try {
            Optional<Product> productById = productService.getProductById(Long.parseLong(productId));
            if (!name.isEmpty() && !description.isEmpty() && price > 0) {
                if (productById.isPresent()) {
                    Product newProduct = new Product(
                            productById.get().getId(), name, description, price);
                    productService.updateProduct(newProduct);
                    return "redirect:/admin/products";
                }
            } else {
                model.addAttribute("product", productById.get());
                model.addAttribute("message", "All fields must be filled!!!");
                return "edit_product";
            }
        } catch (NumberFormatException e) {
            logger.info("Invalid input format.");
            return "product?id=" + productId;
        }
        return "products";
    }

    @GetMapping(path = "/admin/product/delete")
    public String deleteProduct(@RequestParam(value = "id") String productId) {
        if (productId != null) {
            Optional<Product> product = productService.getProductById(Long.parseLong(productId));
            if (product.isPresent()) {
                productService.removeProduct(product.get());
            }
        }
        return "redirect:/admin/products";
    }

    @GetMapping(path = "/admin/add_product")
    public String doGetAddProduct() {
        return "add_product";
    }

    @PostMapping(path = "/admin/add_product")
    public String addProduct(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price) {
        if (!name.isEmpty() && !description.isEmpty() && price > 0) {
            Product newProduct = new Product(name, description, price);
            productService.addProduct(newProduct);
            return "redirect:/admin/products";
        } else {
            model.addAttribute("message", "All fields must be filled correctly!");
            return "add_product";
        }
    }
}
