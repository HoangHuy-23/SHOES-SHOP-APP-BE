package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.cloudinary.CloudinaryProvider;
import iuh.fit.dhktpm117ctt.group06.dto.request.ProductRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.ProductResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Product;
import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductColor;
import iuh.fit.dhktpm117ctt.group06.exception.AppException;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.repository.ProductRepository;
import iuh.fit.dhktpm117ctt.group06.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductRepository productRepository;
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private CloudinaryProvider cloudinaryProvider;

    private ProductResponse mapToProductResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    private Product mapToProduct(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }


    @Transactional
    @Override
    public Optional<ProductResponse> addProduct(ProductRequest productRequest) {
        Product product = mapToProduct(productRequest);
        if (productRequest.getAvatar() != null) {
            try {
                Map uploadResult = cloudinaryProvider.upload(productRequest.getAvatar(), "Product", product.getName());
                product.setAvatar(uploadResult.get("url").toString());
                return Optional.of(mapToProductResponse(productRepository.save(product)));
            } catch (Exception e) {
                throw new AppException(ErrorCode.AVATAR_INVALID);
            }
        }
        return Optional.of(mapToProductResponse(productRepository.save(product)));
    }

    @Transactional
    @Override
    public Optional<ProductResponse> updateProduct(String productId, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            product.get().setName(productRequest.getName());
            product.get().setDescription(productRequest.getDescription());
            if (productRequest.getAvatar() != null && !productRequest.getAvatar().isEmpty()) {
                try {
                    Map uploadResult = cloudinaryProvider.upload(productRequest.getAvatar(), "Product", product.get().getName());
                    product.get().setAvatar(uploadResult.get("url").toString());
                    return Optional.of(mapToProductResponse(productRepository.save(product.get())));
                } catch (Exception e) {
                    throw new AppException(ErrorCode.AVATAR_INVALID);
                }
            }
            
            String imageUrl = product.get().getAvatar();
            product.get().setAvatar(imageUrl);
            return Optional.of(mapToProductResponse(productRepository.save(product.get())));
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    @Override
    public Optional<ProductResponse> updateProductAvatar(String productId, MultipartFile avatar) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            if (avatar == null) {
                return Optional.of(mapToProductResponse(product.get()));
            } else {
                try {
                    Map uploadResult = cloudinaryProvider.upload(avatar, "Product", product.get().getId());
                    product.get().setAvatar(uploadResult.get("url").toString());
                    return Optional.of(mapToProductResponse(productRepository.save(product.get())));
                } catch (Exception e) {
                    throw new AppException(ErrorCode.AVATAR_INVALID);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.search(keyword).stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public ProductResponse getProductById(String productId) {
        return productRepository.findById(productId).map(this::mapToProductResponse).orElse(null);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public List<ProductColor> getProductColors(String productId) {
        return List.of();
    }
}