package com.microservice.inventory_service.service;

import com.microservice.inventory_service.dto.ProductDto;
import com.microservice.inventory_service.entity.ProductEntity;
import com.microservice.inventory_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> findAll() {
        log.info("Fetching all products from the database");
        List<ProductEntity> productEntities = productRepository.findAll();
        log.debug("Found {} products", productEntities.size());
        List<ProductDto> productDto = productEntities.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        log.info("Returning {} product DTOs", productDto.size());
        return productDto;
    }

    public ProductDto findById(Long id) {
        log.info("Fetching product with ID: {}", id);
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        if (productEntity == null) {
            log.warn("Product with ID: {} not found", id);
            return null;
        }
        ProductDto productDto = modelMapper.map(productEntity, ProductDto.class);
        log.info("Product with ID: {} found and mapped to DTO", id);
        return productDto;
    }

}
