package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.Product;

import java.util.List;

public interface ProductService {

    // Role Admin Only Methods
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void removeProduct(String productId);
    void updateProductAvatar(String productId, String newAvatarUrl);

    // All Roles Methods
    List<Product> searchProducts(String keyword); // replace 'keyword' with specific criteria
    Product getProductById(String productId);
    List<Product> getAllProducts();
}