package org.example.cafespring.service;

import org.example.cafespring.model.Product;
import org.example.cafespring.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductDAO productDAO;
    @Autowired
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> findAll() {
        return productDAO.findAll();
    }

    public Product findById(int id) {
        return productDAO.findById(id);
    }

    public void save(Product product) {
        productDAO.add(product);
    }

    public void delete(int id) {
        productDAO.delete(id);
    }

    public void update(Product product) {
        productDAO.update(product);
    }
}
