package com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Order;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.OrderItem;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.OrderTestData.*;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.DiscountTestData.*;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.ProductSkuTestData.*;

public class OrderItemTestData {

    public static List<OrderItem> createListOfOrderItems() {
		OrderItem orderItem1 = createOrderItem001().orElseThrow();
		OrderItem orderItem2 = createOrderItem002().orElseThrow();
		OrderItem orderItem3 = createOrderItem003().orElseThrow();
		OrderItem orderItem4 = createOrderItem004().orElseThrow();
		OrderItem orderItem5 = createOrderItem005().orElseThrow();
		OrderItem orderItem6 = createOrderItem006().orElseThrow();
		OrderItem orderItem7 = createOrderItem007().orElseThrow();

		return List.of(
			orderItem1, 
			orderItem2, 
			orderItem3, 
			orderItem4, 
			orderItem5,
			orderItem6,
			orderItem7
		);
	}
	
	public static Optional<OrderItem> createOrderItem001() {
		OrderItem orderItem = new OrderItem();
		Order order = createOrder001().orElseThrow();
		ProductSku productSku = createProductSku001().orElseThrow();
		Discount discount = createDiscount001().orElseThrow();

		orderItem.setId(1L);
		orderItem.setOrder(order);
		orderItem.setProductSku(productSku);
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(new BigDecimal(1000.00));
		orderItem.setDiscount(discount);
		orderItem.setCreatedAt(Timestamp.from(Instant.now()));
		orderItem.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(orderItem);
	}

	public static Optional<OrderItem> createOrderItem002() {
		OrderItem orderItem = new OrderItem();
		Order order = createOrder001().orElseThrow();
		ProductSku productSku = createProductSku002().orElseThrow();

		orderItem.setId(2L);
		orderItem.setOrder(order);
		orderItem.setProductSku(productSku);
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(new BigDecimal(899.99));
		orderItem.setDiscount(null);
		orderItem.setCreatedAt(Timestamp.from(Instant.now()));
		orderItem.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(orderItem);
	}

	public static Optional<OrderItem> createOrderItem003() {
		OrderItem orderItem = new OrderItem();
		Order order = createOrder002().orElseThrow();
		ProductSku productSku = createProductSku003().orElseThrow();
		Discount discount = createDiscount002().orElseThrow();

		orderItem.setId(3L);
		orderItem.setOrder(order);
		orderItem.setProductSku(productSku);
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(new BigDecimal(1299.99));
		orderItem.setDiscount(discount);
		orderItem.setCreatedAt(Timestamp.from(Instant.now()));
		orderItem.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(orderItem);
	}

	public static Optional<OrderItem> createOrderItem004() {
		OrderItem orderItem = new OrderItem();
		Order order = createOrder002().orElseThrow();
		ProductSku productSku = createProductSku004().orElseThrow();

		orderItem.setId(4L);
		orderItem.setOrder(order);
		orderItem.setProductSku(productSku);
		orderItem.setQuantity(2);
		orderItem.setUnitPrice(new BigDecimal(35.00));
		orderItem.setDiscount(null);
		orderItem.setCreatedAt(Timestamp.from(Instant.now()));
		orderItem.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(orderItem);
	}

	public static Optional<OrderItem> createOrderItem005() {
		OrderItem orderItem = new OrderItem();
		Order order = createOrder003().orElseThrow();
		ProductSku productSku = createProductSku005().orElseThrow();
		Discount discount = createDiscount003().orElseThrow();

		orderItem.setId(5L);
		orderItem.setOrder(order);
		orderItem.setProductSku(productSku);
		orderItem.setQuantity(2);
		orderItem.setUnitPrice(new BigDecimal(25.00));
		orderItem.setDiscount(discount);
		orderItem.setCreatedAt(Timestamp.from(Instant.now()));
		orderItem.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(orderItem);
	}

	public static Optional<OrderItem> createOrderItem006() {
		OrderItem orderItem = new OrderItem();
		Order order = createOrder004().orElseThrow();
		ProductSku productSku = createProductSku001().orElseThrow();

		orderItem.setId(6L);
		orderItem.setOrder(order);
		orderItem.setProductSku(productSku);
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(new BigDecimal(1000.00));
		orderItem.setDiscount(null);
		orderItem.setCreatedAt(Timestamp.from(Instant.now()));
		orderItem.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(orderItem);
	}

	public static Optional<OrderItem> createOrderItem007() {
		OrderItem orderItem = new OrderItem();
		Order order = createOrder005().orElseThrow();
		ProductSku productSku = createProductSku006().orElseThrow();
		Discount discount = createDiscount001().orElseThrow();

		orderItem.setId(7L);
		orderItem.setOrder(order);
		orderItem.setProductSku(productSku);
		orderItem.setQuantity(1);
		orderItem.setUnitPrice(new BigDecimal(50.00));
		orderItem.setDiscount(discount);
		orderItem.setCreatedAt(Timestamp.from(Instant.now()));
		orderItem.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(orderItem);
	}

}
