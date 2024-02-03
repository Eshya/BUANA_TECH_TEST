package com.eshya.buana_technical_test.controller;

import com.eshya.buana_technical_test.exception.ResourceNotFoundException;
import com.eshya.buana_technical_test.model.ProductDetail;
import com.eshya.buana_technical_test.payload.MessageResponse;
import com.eshya.buana_technical_test.payload.product.ProductDetailRequest;
import com.eshya.buana_technical_test.service.ProductDetailService;
import com.eshya.buana_technical_test.utils.Constant;
import com.eshya.buana_technical_test.utils.TimeTraker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/" + Constant.API_NAME + "/product")
public class ProductController {
    private final ProductDetailService productDetailService;

    @Autowired
    public ProductController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }
    /**
     * Create new product
     * @param productDetailRequest
     * @return ProductDetail
     */

    @TimeTraker
    @PostMapping
    public ProductDetail createProduct(@RequestBody ProductDetailRequest request) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setName(request.getName());
        productDetail.setDescription(request.getDescription());
        productDetail.setPrice(request.getPrice());
        productDetail.setQuantity(request.getQuantity());
        return productDetailService.create(productDetail);
    }

    /**
     * Update product
     * @param ProductDetail
     * @return ProductDetail
     */
    @TimeTraker
    @PutMapping
    public ProductDetail updateProduct(@RequestBody ProductDetail request) {
        ProductDetail productDetail = productDetailService.findById(request.getId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Product not found", "id", request.getId()));
        if(request.getName() != null)
            productDetail.setName(request.getName());
        if(request.getDescription() != null)
            productDetail.setDescription(request.getDescription());
        if(request.getPrice() != null)
            productDetail.setPrice(request.getPrice());

        productDetail.setQuantity(request.getQuantity() > 0 ? request.getQuantity() : productDetail.getQuantity());
        return productDetailService.create(productDetail);
    }

    /**
     * Get product by id
     * @param id
     * @return ProductDetail
     */
    @TimeTraker
    @GetMapping("/{id}")
    public ProductDetail getProductById(@PathVariable("id") UUID id) {
        Optional<ProductDetail> productDetailOptional = productDetailService.findById(id);

        if (productDetailOptional.isPresent()) {
            // Product found
            return productDetailOptional.get();
        } else {
            // Product not found
            throw new ResourceNotFoundException("Product not found", "id", id);
        }
    }

    /**
     * Get all product
     * @return List<ProductDetail>
     */
    @TimeTraker
    @GetMapping
    public List<ProductDetail> getAllProduct() {
        return productDetailService.findAll();
    }

    /**
     * Delete product by id
     * @param id
     * @return String
     */
    @TimeTraker
    @DeleteMapping("/{id}")
    public Object deleteProductById(@PathVariable("id") UUID id) {
        productDetailService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found", "id", id));
        productDetailService.deleteById(id);
        return new ResponseEntity<>(new MessageResponse("Product deleted successfully"), HttpStatus.OK);
    }

}
