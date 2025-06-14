package com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Category;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.BrandTestData.*;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.CategoryTestData.*;

public class ProductTestData {

    public static List<Product> createListOfProducts() {
        Product product1 = createProduct001().orElseThrow();
        Product product2 = createProduct002().orElseThrow();
        Product product3 = createProduct003().orElseThrow();
        Product product4 = createProduct004().orElseThrow();
        Product product5 = createProduct005().orElseThrow();

        return List.of(
            product1, 
            product2, 
            product3, 
            product4, 
            product5
        );
    }

    public static Optional<Product> createProduct001() {
        Product product = new Product();
        Category category = createSubCategory001().orElseThrow();
        Brand brand = createBrand002().orElseThrow();

        product.setId(1L);
        product.setName("Notebook Samsung");
        product.setDescription("Computador de ultima generacion Samsung");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct002() {
        Product product = new Product();
        Category category = createSubCategory002().orElseThrow();
        Brand brand = createBrand001().orElseThrow();

        product.setId(2L);
        product.setName("iPhone Apple");
        product.setDescription("Celular de ultima generacion Apple");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image2.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct003() {
        Product product = new Product();
        Category category = createSubCategory001().orElseThrow();
        Brand brand = createBrand001().orElseThrow();

        product.setId(3L);
        product.setName("MacBook Apple");
        product.setDescription("Computador de ultima generacion Apple");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image3.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct004() {
        Product product = new Product();
        Category category = createSubCategory003().orElseThrow();
        Brand brand = createBrand003().orElseThrow();

        product.setId(4L);
        product.setName("Polera manga corta");
        product.setDescription("Polera manga corta de algodon");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image5.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct005() {
        Product product = new Product();
        Category category = createSubCategory004().orElseThrow();
        Brand brand = createBrand003().orElseThrow();

        product.setId(5L);
        product.setName("Jeans Americanino");
        product.setDescription("Jeans Americanino hombre");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image4.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

}
