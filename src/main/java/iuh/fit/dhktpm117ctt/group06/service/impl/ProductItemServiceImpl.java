package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.cloudinary.CloudinaryProvider;
import iuh.fit.dhktpm117ctt.group06.dto.request.ProductItemRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.ProductItemResponse;
import iuh.fit.dhktpm117ctt.group06.entities.ProductItem;
import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductColor;
import iuh.fit.dhktpm117ctt.group06.exception.AppException;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.repository.ProductItemRepository;
import iuh.fit.dhktpm117ctt.group06.service.ProductItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductItemServiceImpl implements ProductItemService {

    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CloudinaryProvider cloudinaryProvider;
    private ModelMapper modelMapper = new ModelMapper();

    private ProductItemResponse mapToProductItemResponse(ProductItem productItem) {
        return modelMapper.map(productItem, ProductItemResponse.class);
    }

    private ProductItem mapToProductItem(ProductItemRequest productItemRequest) {
        return modelMapper.map(productItemRequest, ProductItem.class);
    }

    @Override
    public Optional<ProductItemResponse> save(ProductItemRequest productItemRequest) {
        ProductItem productItem = mapToProductItem(productItemRequest);
        if (productItemRequest.getListDetailImages() != null) {
            try {
                List<Map> uploadResult = cloudinaryProvider.uploadFiles(productItemRequest.getListDetailImages(), "Product-Item", productItem.getId());
                List<String> listDetailImages = new ArrayList<>();
                for (Map map : uploadResult) {
                    listDetailImages.add(map.get("url").toString());
                }
                productItem.setListDetailImages(listDetailImages);
                //return Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
            } catch (Exception e) {
                throw new AppException(ErrorCode.AVATAR_INVALID);
            }
        }
        return Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
    }

    @Override
    public Optional<ProductItemResponse> update(String id, ProductItemRequest productItemRequest) {
        Optional<ProductItem> optionalProductItem = productItemRepository.findById(id);
        if (optionalProductItem.isPresent()) {
            ProductItem productItem = optionalProductItem.get();
            productItem.setPrice(productItemRequest.getPrice());
            productItem.setQuantity(productItemRequest.getQuantity());
            productItem.setColor(ProductColor.valueOf(productItemRequest.getColor()));
            productItem.setSize(productItemRequest.getSize());
            if (productItemRequest.getListDetailImages() != null) {
                try {
                    List<Map> uploadResult = cloudinaryProvider.uploadFiles(productItemRequest.getListDetailImages(), "Product-Item", productItem.getId());
                    List<String> listDetailImages = new ArrayList<>();
                    for (Map map : uploadResult) {
                        listDetailImages.add(map.get("url").toString());
                    }
                    productItem.setListDetailImages(listDetailImages);
                    return Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
                } catch (Exception e) {
                    throw new AppException(ErrorCode.AVATAR_INVALID);
                }
            }
            return Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(String productItemId) {
        productItemRepository.deleteById(productItemId);
    }

    @Override
    public Optional<ProductItemResponse> updateDetailImage(String productItemId, MultipartFile[] newDetailImages) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductItemResponse> updateQuantity(String productItemId, int qty) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductItemResponse> decreaseQuantity(String productItemId, int qty) {
        return Optional.empty();
    }

    @Override
    public List<ProductItemResponse> findByProduct(String productId) {
        List<ProductItem> productItems = productItemRepository.findByProduct(productId);
        return productItems.stream()
                .map(this::mapToProductItemResponse)
                .toList();
    }


    @Override
    public Optional<ProductItemResponse> findByColorAndSize(String color, String size, String productId) {
        return productItemRepository.findByColorAndSizeAndProductId(color, size, productId)
                .map(this::mapToProductItemResponse);
    }

    @Override
    public List<ProductColor> findDistinctColorsByProductId(String productId) {
        return productItemRepository.findDistinctColorsByProductId(productId);
    }

    @Override
    public List<String> findDistinctSizesByProductId(String productId) {
        return productItemRepository.findDistinctSizesByProductId(productId);
    }
}
