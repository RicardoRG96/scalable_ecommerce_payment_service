package com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Address;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Order;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.OrderStatus;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.PaymentStatus;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.AddressTestData.*;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.UserTestData.*;

public class OrderTestData {

    public static List<Order> createListOfOrders() {
		Order order1 = createOrder001().orElseThrow();
		Order order2 = createOrder002().orElseThrow();
		Order order3 = createOrder003().orElseThrow();
		Order order4 = createOrder004().orElseThrow();
		Order order5 = createOrder005().orElseThrow();
		Order order6 = createOrder006().orElseThrow();

		return List.of(order1, order2, order3, order4, order5, order6);
	}

	public static Optional<Order> createOrder001() {
		Order order = new Order();
		User user = createUser001().orElseThrow();
		Address shippingAddress = createAddress001().orElseThrow();
		Address billingAddress = createAddress001().orElseThrow();

		order.setId(1L);
		order.setUser(user);
        order.setTotalAmount(new BigDecimal("199.99"));
		order.setOrderStatus(OrderStatus.valueOf("PENDING"));
		order.setPaymentStatus(PaymentStatus.valueOf("PENDING"));
		order.setShippingAddress(shippingAddress);
		order.setBillingAddress(billingAddress);
		order.setCreatedAt(Timestamp.from(Instant.now()));
		order.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(order);
	}

	public static Optional<Order> createOrder002() {
		Order order = new Order();
		User user = createUser002().orElseThrow();
		Address shippingAddress = createAddress002().orElseThrow();
		Address billingAddress = createAddress002().orElseThrow();

		order.setId(2L);
		order.setUser(user);
		order.setTotalAmount(new BigDecimal("49.99"));
		order.setOrderStatus(OrderStatus.valueOf("PAID"));
		order.setPaymentStatus(PaymentStatus.valueOf("COMPLETED"));
		order.setShippingAddress(shippingAddress);
		order.setBillingAddress(billingAddress);
		order.setCreatedAt(Timestamp.from(Instant.now()));
		order.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(order);
	}

	public static Optional<Order> createOrder003() {
		Order order = new Order();
		User user = createUser003().orElseThrow();
		Address shippingAddress = createAddress003().orElseThrow();
		Address billingAddress = createAddress003().orElseThrow();

		order.setId(3L);
		order.setUser(user);
		order.setTotalAmount(new BigDecimal("89.99"));
		order.setOrderStatus(OrderStatus.valueOf("SHIPPED"));
		order.setPaymentStatus(PaymentStatus.valueOf("COMPLETED"));
		order.setShippingAddress(shippingAddress);
		order.setBillingAddress(billingAddress);
		order.setCreatedAt(Timestamp.from(Instant.now()));
		order.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(order);
	}

	public static Optional<Order> createOrder004() {
		Order order = new Order();
		User user = createUser001().orElseThrow();
		Address shippingAddress = createAddress001().orElseThrow();
		Address billingAddress = createAddress001().orElseThrow();

		order.setId(4L);
		order.setUser(user);
		order.setTotalAmount(new BigDecimal("39.99"));
		order.setOrderStatus(OrderStatus.valueOf("PAID"));
		order.setPaymentStatus(PaymentStatus.valueOf("COMPLETED"));
		order.setShippingAddress(shippingAddress);
		order.setBillingAddress(billingAddress);
		order.setCreatedAt(Timestamp.from(Instant.now()));
		order.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(order);
	}

	public static Optional<Order> createOrder005() {
		Order order = new Order();
		User user = createUser002().orElseThrow();
		Address shippingAddress = createAddress002().orElseThrow();
		Address billingAddress = createAddress002().orElseThrow();

		order.setId(5L);
		order.setUser(user);
		order.setTotalAmount(new BigDecimal("99.99"));
		order.setOrderStatus(OrderStatus.valueOf("SHIPPED"));
		order.setPaymentStatus(PaymentStatus.valueOf("COMPLETED"));
		order.setShippingAddress(shippingAddress);
		order.setBillingAddress(billingAddress);
		order.setCreatedAt(Timestamp.from(Instant.now()));
		order.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(order);
	}

	public static Optional<Order> createOrder006() {
		Order order = new Order();
		User user = createUser003().orElseThrow();
		Address shippingAddress = createAddress003().orElseThrow();
		Address billingAddress = createAddress003().orElseThrow();

		order.setId(6L);
		order.setUser(user);
		order.setTotalAmount(new BigDecimal("149.99"));
		order.setOrderStatus(OrderStatus.valueOf("PAID"));
		order.setPaymentStatus(PaymentStatus.valueOf("PENDING"));
		order.setShippingAddress(shippingAddress);
		order.setBillingAddress(billingAddress);
		order.setCreatedAt(Timestamp.from(Instant.now()));
		order.setUpdatedAt(Timestamp.from(Instant.now()));

		return Optional.of(order);
	}

}
