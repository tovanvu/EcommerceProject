package com.vti.service.orders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.vti.config.specification.OrderSpecification;
import com.vti.model.dto.GroupsVariantsDTO;
import com.vti.model.dto.OrderItemsDTO;
import com.vti.model.dto.ProductsDTO;
import com.vti.model.dto.request.OrderItemRequest;
import com.vti.model.dto.request.OrderRequest;
import com.vti.model.dto.request.OrderUpdateRequest;
import com.vti.model.dto.response.OrdersRespone;
import com.vti.model.entity.GroupVariants;
import com.vti.model.entity.OrderItems;
import com.vti.model.entity.Orders;
import com.vti.model.entity.Products;
import com.vti.repository.IGroupVariantRepository;
import com.vti.repository.IOrderItemRepository;
import com.vti.repository.IOrderRepository;
import com.vti.repository.IProductsRepository;
import com.vti.ultils.MappingOperatorUtils;
import com.vti.exception.NoDataFoundException;

@Service
public class OrdersService implements IOrdersService {
	@Autowired
	private IOrderRepository repository;
	@Autowired
	private IProductsRepository productsRepository;
	@Autowired
	private IGroupVariantRepository variantRepository;
	@Autowired
	private IOrderItemRepository itemRepository;
	@Autowired
	private MessageSource messageSource;

	@Override
	public List<OrdersRespone> searchOrders(Pageable pageable, String name, String variant_name, String product_name,
			Long id, String address, Long total_price, String operator) {
		Specification<Orders> where = null;
		if (!ObjectUtils.isEmpty(name)) {
			OrderSpecification nameSpecification = new OrderSpecification("name", "LIKE", name);
			where = Specification.where(nameSpecification);
		}
		if (!ObjectUtils.isEmpty(variant_name)) {
			OrderSpecification variantNameSpecification = new OrderSpecification("variantName", "LIKE", variant_name);
			if (where == null) {
				where = Specification.where(variantNameSpecification);
			} else {
				where = where.and(variantNameSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(id)) {
			OrderSpecification idSpecification = new OrderSpecification("id", "=", id);
			if (where == null) {
				where = Specification.where(idSpecification);
			} else {
				where = where.and(idSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(product_name)) {
			OrderSpecification productNameSpecification = new OrderSpecification("productName", "LIKE", product_name);
			if (where == null) {
				where = Specification.where(productNameSpecification);
			} else {
				where = where.and(productNameSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(address)) {
			OrderSpecification addressSpecification = new OrderSpecification("address", "LIKE", address);
			if (where == null) {
				where = Specification.where(addressSpecification);
			} else {
				where = where.and(addressSpecification);
			}
		}
		if (!ObjectUtils.isEmpty(total_price)) {
			OrderSpecification totalPriceSpecification = new OrderSpecification("totalPrice",
					MappingOperatorUtils.get(operator), total_price);
			if (where == null) {
				where = Specification.where(totalPriceSpecification);
			} else {
				where = where.and(totalPriceSpecification);
			}
		}
		Page<Orders> pageOrders = repository.findAll(where, pageable);
		List<Orders> listOrders = pageOrders.getContent();
		List<OrdersRespone> listRespones = new ArrayList<>();
		for (Orders listOrder : listOrders) {
			List<OrderItems> listItems = listOrder.getOrderItems();
			List<OrderItemsDTO> listItemDto = new ArrayList<>();
			for (OrderItems listItem : listItems) {
				Products products = listItem.getProduct();
				GroupVariants variants = listItem.getGroupVariant();
				ProductsDTO productsDTO = new ProductsDTO(products.getId(), products.getProductName(),
						products.getPrice());
				GroupsVariantsDTO variantsDTO = new GroupsVariantsDTO(variants.getId(), variants.getVariantName());
				OrderItemsDTO itemsDTO = new OrderItemsDTO(productsDTO, listItem.getQuantity(), variantsDTO);
				listItemDto.add(itemsDTO);
			}
			OrdersRespone ordersRespone = new OrdersRespone(listOrder.getId(), listOrder.getName(),
					listOrder.getAddress(), listOrder.getCity(), listOrder.getZip(), listOrder.getStatus(),
					listOrder.getComment(), listOrder.getTotalPrice(), listOrder.getType(), listOrder.getCreated(),
					listItemDto);
			listRespones.add(ordersRespone);
		}
		return listRespones;
	}

	@Override
	@Transactional
	public OrdersRespone AddOrder(OrderRequest orderRequest) {
		long totalPrice = 0;
		Orders orders = new Orders();
		orders.setName(orderRequest.getName());
		orders.setAddress(orderRequest.getAddress());
		orders.setCity(orderRequest.getCity());
		orders.setZip(orderRequest.getZip());
		orders.setComment(orderRequest.getComment());
		orders.setType(orderRequest.getType());
		orders.setCreated(LocalDate.now());
		repository.save(orders);

		List<OrderItems> listItems = new ArrayList<>();
		List<OrderItemRequest> listOrderItemRequests = orderRequest.getItems();
		List<OrderItemsDTO> listItemsdtos = new ArrayList<>();
		for (OrderItemRequest orderItemRequests : listOrderItemRequests) {
			Products products = productsRepository.getById(orderItemRequests.getProduct().getId());
			GroupVariants variants = variantRepository.getById(orderItemRequests.getVariant().getId());
			OrderItems orderItems = new OrderItems();
			orderItems.setProduct(products);
			orderItems.setGroupVariant(variants);
			orderItems.setQuantity(orderItemRequests.getQuantity());
			orderItems.setOrder(orders);
			listItems.add(orderItems);

			totalPrice += orderItems.getQuantity() * products.getPrice();

			ProductsDTO productsDTO = new ProductsDTO(products.getId(), products.getProductName(), products.getPrice());
			GroupsVariantsDTO variantsDTO = new GroupsVariantsDTO(variants.getId(), variants.getVariantName());
			OrderItemsDTO orderItemsDTO = new OrderItemsDTO(productsDTO, orderItems.getQuantity(), variantsDTO);
			listItemsdtos.add(orderItemsDTO);
		}
		itemRepository.saveAll(listItems);
		orders.setTotalPrice(totalPrice);
		orders.setOrderItems(listItems);

		OrdersRespone ordersRespone = new OrdersRespone(orders.getId(), orders.getName(), orders.getAddress(),
				orders.getCity(), orders.getZip(), orders.getStatus(), orders.getComment(), orders.getTotalPrice(),
				orders.getType(), orders.getCreated(), listItemsdtos);
		return ordersRespone;
	}

	@Override
	public OrdersRespone OrderDetail(long id) {
		Orders orders = repository.findById(id).orElse(null);
		if (orders == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.order.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}
		List<OrderItems> listItems = orders.getOrderItems();
		List<OrderItemsDTO> listItemsDTOs = new ArrayList<>();
		for (OrderItems Items : listItems) {
			Products products = Items.getProduct();
			GroupVariants variants = Items.getGroupVariant();
			ProductsDTO productsDTO = new ProductsDTO(products.getId(), products.getProductName(), products.getPrice());
			GroupsVariantsDTO variantsDTO = new GroupsVariantsDTO(variants.getId(), variants.getVariantName());
			OrderItemsDTO itemsDTO = new OrderItemsDTO(productsDTO, Items.getQuantity(), variantsDTO);
			listItemsDTOs.add(itemsDTO);
		}
		OrdersRespone ordersRespone = new OrdersRespone(orders.getId(), orders.getName(), orders.getAddress(),
				orders.getCity(), orders.getZip(), orders.getStatus(), orders.getComment(), orders.getTotalPrice(),
				orders.getType(), orders.getCreated(), listItemsDTOs);
		return ordersRespone;
	}

	@Transactional
	@Modifying
	@Override
	public OrdersRespone UpdateOrder(long id, OrderUpdateRequest orderRequest) {
		Orders orders = repository.findById(id).orElse(null);
		if (orders == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.order.id.notfound",
					new String[] { Long.toString(id) }, Locale.getDefault()));
		}

		long totalPrice = 0;
		List<OrderItems> listItems = new ArrayList<>();
		List<OrderItemsDTO> listItemsdtos = new ArrayList<>();

		if (orderRequest.getItems() == null) {
			List<OrderItems> listItemBeforeUpdate = orders.getOrderItems();
			for (OrderItems orderItem : listItemBeforeUpdate) {
				Products products = productsRepository.getById(orderItem.getProduct().getId());
				GroupVariants variants = variantRepository.getById(orderItem.getGroupVariant().getId());

				ProductsDTO productsDTO = new ProductsDTO(products.getId(), products.getProductName(),
						products.getPrice());
				GroupsVariantsDTO variantsDTO = new GroupsVariantsDTO(variants.getId(), variants.getVariantName());
				OrderItemsDTO orderItemsDTO = new OrderItemsDTO(productsDTO, orderItem.getQuantity(), variantsDTO);
				listItemsdtos.add(orderItemsDTO);
			}
		}

		if (orderRequest.getItems() != null) {
			List<OrderItems> listItemBeforeUpdate = orders.getOrderItems();
			itemRepository.deleteAll(listItemBeforeUpdate);

			List<OrderItemRequest> listOrderItemRequests = orderRequest.getItems();
			for (OrderItemRequest orderItemRequests : listOrderItemRequests) {
				Products products = productsRepository.getById(orderItemRequests.getProduct().getId());
				GroupVariants variants = variantRepository.getById(orderItemRequests.getVariant().getId());
				OrderItems orderItems = new OrderItems();
				orderItems.setProduct(products);
				orderItems.setGroupVariant(variants);
				orderItems.setQuantity(orderItemRequests.getQuantity());
				orderItems.setOrder(orders);
				listItems.add(orderItems);

				totalPrice += orderItems.getQuantity() * products.getPrice();

				ProductsDTO productsDTO = new ProductsDTO(products.getId(), products.getProductName(),
						products.getPrice());
				GroupsVariantsDTO variantsDTO = new GroupsVariantsDTO(variants.getId(), variants.getVariantName());
				OrderItemsDTO orderItemsDTO = new OrderItemsDTO(productsDTO, orderItems.getQuantity(), variantsDTO);
				listItemsdtos.add(orderItemsDTO);
			}
			itemRepository.saveAll(listItems);
		}
		if (orderRequest.getName() != null) {
			orders.setName(orderRequest.getName());
		}
		if (orderRequest.getAddress() != null) {
			orders.setAddress(orderRequest.getAddress());
		}
		if (orderRequest.getCity() != null) {
			orders.setCity(orderRequest.getCity());
		}
		if (orderRequest.getZip() != null) {
			orders.setZip(orderRequest.getZip());
		}
		if (orderRequest.getComment() != null) {
			orders.setComment(orderRequest.getComment());
		}
		if (orderRequest.getType() != null) {
			orders.setType(orderRequest.getType());
		}
		if (orderRequest.getItems() != null) {
			orders.setTotalPrice(totalPrice);
			orders.setOrderItems(listItems);
		}
		repository.save(orders);
		OrdersRespone ordersRespone = new OrdersRespone(orders.getId(), orders.getName(), orders.getAddress(),
				orders.getCity(), orders.getZip(), orders.getStatus(), orders.getComment(), orders.getTotalPrice(),
				orders.getType(), orders.getCreated(), listItemsdtos);
		return ordersRespone;
	}

}
