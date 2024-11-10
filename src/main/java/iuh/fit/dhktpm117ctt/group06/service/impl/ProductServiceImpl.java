package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Product;
import iuh.fit.dhktpm117ctt.group06.exception.AppException;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.repository.ProductRepository;
import iuh.fit.dhktpm117ctt.group06.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        // Save the product to the database
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        // Check if the product exists before updating
        Optional<Product> existingProduct = productRepository.findById(product.getId());
        existingProduct.get().setDescription(product.getDescription());
        existingProduct.get().setRating(product.getRating());
        existingProduct.get().setAvatar(product.getAvatar());
        existingProduct.get().setCreatedDate(product.getCreatedDate());
        return productRepository.save(existingProduct.get());
    }

    @Override
    public void removeProduct(String productId) {
        // Check if the product exists before deleting
        productRepository.deleteById(productId);
    }

    @Override
    public void updateProductAvatar(String productId, String newAvatarUrl) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.get();
        // Cập nhật avatar URL mới
        product.setAvatar(newAvatarUrl);
        // Lưu lại thay đổi
        productRepository.save(product);

    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return List.of(); // Replace with actual implementation based on search criteria
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}