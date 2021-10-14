package com.vti.service.cart;

import java.util.List;

import com.vti.model.dto.ItemInCart;
import com.vti.model.dto.request.CreateItemFromCartRequest;
import com.vti.model.dto.response.OrdersRespone;
import com.vti.model.entity.Cart;

public interface ICartService {
	public Cart GenerateCartID();

	public List<ItemInCart> GetIteamForCart(String id);

	public void AddItemToCart(String id, ItemInCart itemInCart);

	public void RemoveCart(String id, long product_id);

	public void UpdateCart(String id, ItemInCart itemInCart);

	public OrdersRespone CreateItemForCart(String id, CreateItemFromCartRequest request);
}
