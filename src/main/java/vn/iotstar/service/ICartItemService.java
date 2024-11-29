package vn.iotstar.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.iotstar.entity.CartItem;


@Service
public interface ICartItemService {

	<S extends CartItem> S save(S entity);

	Optional<CartItem> findByCart_CartIdAndProduct_ProductId(int cartId, int productId);


}
