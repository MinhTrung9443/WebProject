package vn.iotstar.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ShoppingCart")
public class ShoppingCart implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int shoppingCartId;
	
	@OneToOne()
	@JoinColumn(name = "id")
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<CartItem> items;
	
	public void addCartItems(CartItem item)
	{
		getItems().add(item);
		item.setCart(this);
	}
	public void removeCartItem(CartItem item)
	{
		getItems().remove(item);
		item.setCart(null);
	}

}
