package com.vti.service.cart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.vti.exception.NoDataFoundException;
import com.vti.model.dto.GroupsVariantsDTO;
import com.vti.model.dto.ItemInCart;
import com.vti.model.dto.OrderItemsDTO;
import com.vti.model.dto.ProductsDTO;
import com.vti.model.dto.request.CreateItemFromCartRequest;
import com.vti.model.dto.response.OrdersRespone;
import com.vti.model.entity.Cart;
import com.vti.model.entity.GroupVariants;
import com.vti.model.entity.OrderItems;
import com.vti.model.entity.Orders;
import com.vti.model.entity.Products;
import com.vti.repository.IGroupVariantRepository;
import com.vti.repository.IOrderItemRepository;
import com.vti.repository.IOrderRepository;
import com.vti.repository.IProductsRepository;
import com.vti.repository.IRedisCartRepository;

@Service
public class CartService implements ICartService {
	@Autowired
	private IRedisCartRepository cartRepository;

	@Autowired
	private IGroupVariantRepository groupRepository;

	@Autowired
	private IProductsRepository productRepository;

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IOrderItemRepository itemRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Cart GenerateCartID() {
		String id = UUID.randomUUID().toString();
		Cart cart = new Cart(id, new ArrayList<>());
		cartRepository.save(cart);
		return cart;
	}

	@Override
	public List<ItemInCart> GetIteamForCart(String id) {
		Cart cart = cartRepository.findById(id).orElse(null);
		if (cart == null) {
			throw new NoDataFoundException(
					messageSource.getMessage("Get.cart.id.notfound", new String[] { id }, Locale.getDefault()));
		}
		List<ItemInCart> listItem = cart.getItem();
		return listItem;
	}

	@Override
	public void AddItemToCart(String id, ItemInCart itemInCart) {
		Cart cart = cartRepository.findById(id).orElse(null);
		if (cart == null) {
			throw new NoDataFoundException(
					messageSource.getMessage("Get.cart.id.notfound", new String[] { id }, Locale.getDefault()));
		}
		List<ItemInCart> newListItem = new ArrayList<>();
		boolean isNewItem = true;
		List<ItemInCart> listItem = cart.getItem();
		if (listItem != null) {
			for (ItemInCart item : listItem) {
				if (itemInCart.getProductID() == item.getProductID()
						&& itemInCart.getVariantID() == item.getVariantID()) {

					item.setQuantity(itemInCart.getQuantity() + item.getQuantity());
					isNewItem = false;
				}
				newListItem.add(item);
				cart.setItem(newListItem);
			}
			if (isNewItem) {
				listItem.add(itemInCart);
				cart.setItem(listItem);
			}
		}
		if (listItem == null) {
			newListItem.add(itemInCart);
			cart.setItem(newListItem);
		}
		cartRepository.save(cart);
		return;
	}

	@Override
	public void RemoveCart(String id, long product_id) {
		Cart cart = cartRepository.findById(id).orElse(null);
		if (cart == null) {
			throw new NoDataFoundException(
					messageSource.getMessage("Get.cart.id.notfound", new String[] { id }, Locale.getDefault()));
		}
		Products products = productRepository.findById(product_id).orElse(null);
		if (products == null) {
			throw new NoDataFoundException(messageSource.getMessage("Get.product.id.notfound",
					new String[] { Long.toString(product_id) }, Locale.getDefault()));
		}

		List<ItemInCart> listItems = cart.getItem();
		for (Iterator<ItemInCart> iterator = listItems.iterator(); iterator.hasNext();) {
			ItemInCart itemInCart = (ItemInCart) iterator.next();
			if (itemInCart.getProductID() == product_id) {
				iterator.remove();
			}
//			else {
//				throw new NoDataFoundException(messageSource.getMessage("Get.product.id.notfound.in.cart",
//						new String[] { Long.toString(product_id) }, Locale.getDefault()));
//			}
		}
		cart.setItem(listItems);
		cartRepository.save(cart);
		return;
	}

	@Transactional
	@Override
	public void UpdateCart(String id, ItemInCart itemInCart) {
		Cart cart = cartRepository.findById(id).orElse(null);
		if (cart == null) {
			throw new NoDataFoundException(
					messageSource.getMessage("Get.cart.id.notfound", new String[] { id }, Locale.getDefault()));
		}
		List<ItemInCart> ListItem = cart.getItem();
		for (ItemInCart item : ListItem) {
			if (item.getProductID() == itemInCart.getProductID() && itemInCart.getVariantID() == item.getVariantID()) {
				item.setQuantity(itemInCart.getQuantity());
			}
//			if (item.getProductID() == itemInCart.getProductID() && itemInCart.getVariantID() != item.getVariantID()) {
//				throw new NoDataFoundException("Item is not exists in cart");
//			}
//			if (item.getProductID() != itemInCart.getProductID() && itemInCart.getVariantID() == item.getVariantID()) {
//				throw new NoDataFoundException("Item is not exists in cart");
//			}
		}
		cart.setItem(ListItem);
		cartRepository.save(cart);
		return;
	}

	@Transactional
	@Override
	public OrdersRespone CreateItemForCart(String id, CreateItemFromCartRequest ordersRequest) {
		Cart cart = cartRepository.findById(id).orElse(null);
		if (cart == null) {
			throw new NoDataFoundException(
					messageSource.getMessage("Get.cart.id.notfound", new String[] { id }, Locale.getDefault()));
		}
		long totalPrice = 0;
		Orders orders = new Orders();
		orders.setName(ordersRequest.getName());
		orders.setAddress(ordersRequest.getAddress());
		orders.setCity(ordersRequest.getCity());
		orders.setZip(ordersRequest.getZip());
		orders.setComment(ordersRequest.getComment());
		orders.setType(ordersRequest.getType());
		orders.setCreated(LocalDate.now());
		orderRepository.save(orders);

		List<ItemInCart> listItemInCarts = cart.getItem();
		List<OrderItems> listOrderItems = new ArrayList<>();
		List<OrderItemsDTO> listItemsDTOs = new ArrayList<>();
		for (ItemInCart itemInCarts : listItemInCarts) {
			GroupVariants groupVariants = groupRepository.getById(itemInCarts.getVariantID());
			Products products = productRepository.getById(itemInCarts.getProductID());

			OrderItems orderItems = new OrderItems();
			orderItems.setOrder(orders);
			orderItems.setGroupVariant(groupVariants);
			orderItems.setProduct(products);
			orderItems.setQuantity(itemInCarts.getQuantity());
			listOrderItems.add(orderItems);

			totalPrice += orderItems.getQuantity() * products.getPrice();

			ProductsDTO productsDTO = new ProductsDTO(products.getId(), products.getProductName(), products.getPrice());
			GroupsVariantsDTO groupsVariantsDTO = new GroupsVariantsDTO(groupVariants.getId(),
					groupVariants.getVariantName());
			OrderItemsDTO itemDto = new OrderItemsDTO(productsDTO, orderItems.getQuantity(), groupsVariantsDTO);
			listItemsDTOs.add(itemDto);
		}
		itemRepository.saveAll(listOrderItems);
		orders.setOrderItems(listOrderItems);
		orders.setTotalPrice(totalPrice);

		OrdersRespone ordersRespone = new OrdersRespone(orders.getId(), orders.getName(), orders.getAddress(),
				orders.getCity(), orders.getZip(), orders.getStatus(), orders.getComment(), orders.getTotalPrice(),
				orders.getType(), orders.getCreated(), listItemsDTOs);

		cartRepository.deleteById(id);
		return ordersRespone;
	}
}
