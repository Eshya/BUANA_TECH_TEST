package com.eshya.buana_technical_test.service;

import com.eshya.buana_technical_test.model.ProductDetail;
import com.eshya.buana_technical_test.payload.product.ProductDetailRequest;
import com.eshya.buana_technical_test.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    @Autowired
    public ProductDetailService(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    // find by id
    public Optional<ProductDetail> findById(UUID id) {
        return productDetailRepository.findById(id);
    }

    // find All
    public List<ProductDetail> findAll() {
        return productDetailRepository.findAll();
    }

    public ProductDetail create(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }


    public void deleteById(UUID id) {
        productDetailRepository.deleteById(id);
    }
}
