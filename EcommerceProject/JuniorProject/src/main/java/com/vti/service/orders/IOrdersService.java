package com.vti.service.orders;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vti.model.dto.request.OrderRequest;
import com.vti.model.dto.request.OrderUpdateRequest;
import com.vti.model.dto.response.OrdersRespone;

public interface IOrdersService {
	public List<OrdersRespone> searchOrders(Pageable pageable, String name, String variant_name, String product_name,
			Long id, String address, Long total_price, String operator);

	public OrdersRespone AddOrder(OrderRequest orderRequest);

	public OrdersRespone OrderDetail(long id);

	public OrdersRespone UpdateOrder(long id, OrderUpdateRequest orderRequest);
}
