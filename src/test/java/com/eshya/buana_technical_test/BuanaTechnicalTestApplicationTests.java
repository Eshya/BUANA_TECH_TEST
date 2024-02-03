package com.eshya.buana_technical_test;

import com.eshya.buana_technical_test.controller.ProductController;
import com.eshya.buana_technical_test.model.ProductDetail;
import com.eshya.buana_technical_test.payload.MessageResponse;
import com.eshya.buana_technical_test.payload.product.ProductDetailRequest;
import com.eshya.buana_technical_test.service.ProductDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BuanaTechnicalTestApplicationTests {
	@Test
	public void test_getProductById_validInput_returnsProductDetail() {
		// Arrange
		ProductDetailService productDetailService = mock(ProductDetailService.class);
		ProductController productController = new ProductController(productDetailService);
		UUID id = UUID.randomUUID();
		ProductDetail expectedProduct = new ProductDetail();
		expectedProduct.setId(id);
		expectedProduct.setName("Test Product");
		expectedProduct.setDescription("Test Description");
		expectedProduct.setPrice(BigDecimal.valueOf(10.0));
		expectedProduct.setQuantity(5);
		when(productDetailService.findById(id)).thenReturn(Optional.of(expectedProduct));

		// Act
		ProductDetail actualProduct = productController.getProductById(id);

		// Assert
		assertEquals(expectedProduct, actualProduct);
	}

	@Test
	public void test_createUpdateDeleteProduct_validInput() {
		// Arrange
		ProductDetailService productDetailService = mock(ProductDetailService.class);
		ProductController productController = new ProductController(productDetailService);
		ProductDetailRequest request = new ProductDetailRequest();
		request.setName("Test Product");
		request.setDescription("Test Description");
		request.setPrice(BigDecimal.valueOf(10.0));
		request.setQuantity(5);
		ProductDetail expectedProduct = new ProductDetail();
		expectedProduct.setId(UUID.randomUUID());
		expectedProduct.setName("Test Product");
		expectedProduct.setDescription("Test Description");
		expectedProduct.setPrice(BigDecimal.valueOf(10.0));
		expectedProduct.setQuantity(5);
		when(productDetailService.create(any(ProductDetail.class))).thenReturn(expectedProduct);

		// Act
		ProductDetail actualProduct = productController.createProduct(request);

		// Assert
		assertEquals(expectedProduct, actualProduct);

		// Update product
		request.setName("Updated Test Product");
		request.setDescription("Updated Test Description");
		request.setPrice(BigDecimal.valueOf(20.0));
		request.setQuantity(10);
		expectedProduct.setName("Updated Test Product");
		expectedProduct.setDescription("Updated Test Description");
		expectedProduct.setPrice(BigDecimal.valueOf(20.0));
		expectedProduct.setQuantity(10);
		when(productDetailService.findById(expectedProduct.getId())).thenReturn(Optional.of(expectedProduct));

		// Act
		actualProduct = productController.updateProduct(expectedProduct);

		// Assert
		assertEquals(expectedProduct, actualProduct);

		// Delete product
		when(productDetailService.findById(expectedProduct.getId())).thenReturn(Optional.of(expectedProduct));

		// Act
		Object response = productController.deleteProductById(expectedProduct.getId());

		// Assert
		assertNotNull(response);
		assertTrue(response instanceof ResponseEntity);
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody() instanceof MessageResponse);
		MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
		assertEquals("Product deleted successfully", messageResponse.getMessage());
	}

}
