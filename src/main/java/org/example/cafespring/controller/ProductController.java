package org.example.cafespring.controller;

import org.example.cafespring.model.Product;
import org.example.cafespring.repository.ProductDAO;
import org.example.cafespring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @PostMapping("/products/delete")
    public String deleteProduct(@ModelAttribute Product product) {
        productService.delete(product.getId());
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", productService.findById(id));
        return "edit-product";
    }

    @PostMapping("/products/edit")
    public String editProduct(@ModelAttribute("product") Product product) {
        productService.update(product);
        return "redirect:/products";
    }
}
