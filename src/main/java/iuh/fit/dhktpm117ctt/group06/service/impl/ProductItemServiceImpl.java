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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

	@Transactional
	@Override
	public Optional<ProductItemResponse> save(ProductItemRequest productItemRequest) {
		ProductItem productItem = mapToProductItem(productItemRequest);
		if (productItemRequest.getListDetailImages() != null) {
			try {
				List<Map> uploadResult = cloudinaryProvider.uploadFiles(productItemRequest.getListDetailImages(),
						"Product-Item", productItem.getProduct().getId());
				List<String> listDetailImages = new ArrayList<>();
				for (Map map : uploadResult) {
					listDetailImages.add(map.get("url").toString());
				}
				productItem.setListDetailImages(listDetailImages);
				// return
				// Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
			} catch (Exception e) {
				throw new AppException(ErrorCode.AVATAR_INVALID);
			}
		}
		return Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
	}

	@Transactional
	@Override
	public Optional<ProductItemResponse> update(String id, ProductItemRequest productItemRequest) {
		System.out.println("API update được gọi lần đầu tiên: " + System.currentTimeMillis());
		Optional<ProductItem> optionalProductItem = productItemRepository.findById(id);
		if (optionalProductItem.isPresent()) {
			ProductItem productItem = optionalProductItem.get();
			productItem.setPrice(productItemRequest.getPrice());
			productItem.setQuantity(productItemRequest.getQuantity());
			productItem.setColor(ProductColor.valueOf(productItemRequest.getColor()));
			productItem.setSize(productItemRequest.getSize());

			// Lấy danh sách hình ảnh cũ
			List<String> oldImages = productItem.getListDetailImages();

			// Xóa hình ảnh cũ nếu có
			if (productItemRequest.getListImagesDelete() != null) {
				productItemRequest.getListImagesDelete()
						.stream()
						.sorted((a, b) -> b - a) // Sắp xếp giảm dần
						.forEach(index -> {
							if (index >= 0 && index < oldImages.size()) {
								try {
									String imageToDelete = oldImages.get(index);
									cloudinaryProvider.delete(extractPublicIdFromUrl(imageToDelete));
									oldImages.remove(index.intValue());
								} catch (Exception e) {
									throw new AppException(ErrorCode.AVATAR_INVALID);
								}
							}
						});
			}


			// Thêm hình ảnh mới
			var files = productItemRequest.getListDetailImages();
			if (files != null && files.length > 0) {
				List<MultipartFile> nonEmptyFiles = new ArrayList<>();
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						nonEmptyFiles.add(file);
					}
				}

				if (!nonEmptyFiles.isEmpty()) {
					try {
						System.out.println("Non empty files: " + nonEmptyFiles.size());
						List<Map> uploadResult = cloudinaryProvider.uploadFiles(nonEmptyFiles.toArray(new MultipartFile[0]),
								"Product-Item", productItem.getProduct().getId());
						for (Map map : uploadResult) {
							String newImageUrl = map.get("url").toString();
							// Chỉ thêm nếu không trùng lặp
							if (!oldImages.contains(newImageUrl)) {
								oldImages.add(newImageUrl);
							}
						}
					} catch (Exception e) {
						throw new AppException(ErrorCode.AVATAR_INVALID);
					}
				}
			}

			// Cập nhật danh sách hình ảnh và lưu sản phẩm
			productItem.setListDetailImages(oldImages);
			return Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
		}

		return Optional.empty();
	}


	@Override
	@Transactional
	public Optional<ProductItemResponse> updateQuantity(String id, int quantity) {
		Optional<ProductItem> optionalProductItem = productItemRepository.findById(id);
		if (optionalProductItem.isPresent()) {
			ProductItem productItem = optionalProductItem.get();
			if (quantity != 0) {
				System.out.println("Quantity: " + quantity);
				productItem.setQuantity(quantity);
				return Optional.of(mapToProductItemResponse(productItemRepository.save(productItem)));
			} else {
				throw new AppException(ErrorCode.QTY_INVALID);
			}

		}
		return Optional.empty();
	}

	@Transactional
	@Override
	public void deleteById(String productItemId) {

		if (productItemRepository.exexistedByOrders(productItemId).size() > 0) {
			throw new AppException(ErrorCode.PRODUCT_ITEM_EXISTED_IN_ORDER_DETAILS);
		}

		productItemRepository.deleteById(productItemId);
	}

	@Override
	public Optional<ProductItemResponse> updateDetailImage(String productItemId, MultipartFile[] newDetailImages) {
		return Optional.empty();
	}

	@Override
	public List<ProductItemResponse> findByProduct(String productId) {
		List<ProductItem> productItems = productItemRepository.findByProduct(productId);
		return productItems.stream().map(this::mapToProductItemResponse).toList();
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

	@Override
	public List<ProductItem> findAll() {
		return productItemRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<ProductItem> findById(String id) {
		return productItemRepository.findById(id);
	}
	
	@Override
	public Page<ProductItem> listTopSaleProductItems(Pageable pageable){
		return productItemRepository.listTopSaleProductItems(pageable);
	}
	
	@Override
	public Page<ProductItem> listNewProductItems(Pageable pageable) {
		return productItemRepository.listNewProductItems(pageable);
	}

	public String extractPublicIdFromUrl(String url) {
		try {
			String path = new java.net.URI(url).getPath();
			// Extract publicId from path (after last "/" and before ".")
			return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		} catch (Exception e) {
			throw new AppException(ErrorCode.AVATAR_INVALID);
		}
	}

}
