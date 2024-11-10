package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.dto.request.BrandRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.BrandResponse;
import iuh.fit.dhktpm117ctt.group06.dto.response.UserResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Brand;
import iuh.fit.dhktpm117ctt.group06.repository.BrandRepository;
import iuh.fit.dhktpm117ctt.group06.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    private ModelMapper modelMapper = new ModelMapper();

    public Brand mapToBrand(BrandRequest brandRequest) {
        return modelMapper.map(brandRequest, Brand.class);
    }

    public BrandResponse mapToBrandResponse(Brand brand) {
        return modelMapper.map(brand, BrandResponse.class);
    }
    @Override
    public Optional<BrandRepository> findById(String id) {
        Brand brand = brandRepository.findById(id).orElse(null);
        return Optional.of((BrandRepository) mapToBrandResponse(brand));
    }

    @Override
    public Optional<BrandResponse> save(BrandRequest brandRequest) {
        Brand brand = mapToBrand(brandRequest);
        return Optional.of(mapToBrandResponse(brandRepository.save(brand)));
    }

    @Override
    public Optional<BrandResponse> update(String id,BrandRequest brandRequest) {
        Brand brand = brandRepository.getReferenceById(id);
        Brand updatedBrand = mapToBrand(brandRequest);
        updatedBrand.setId(brand.getId());
        return Optional.of(mapToBrandResponse(brandRepository.save(updatedBrand)));
    }


    @Override
    public void deleteById(String id) {
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandResponse> findAll() {
        var brands = brandRepository.findAll();
        return brands.stream().map(this::mapToBrandResponse).toList();
    }
}