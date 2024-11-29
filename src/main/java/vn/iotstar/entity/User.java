package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "[User]")
public class User extends Person implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date birthday;
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shoppingCartId")
	@JsonManagedReference
	private ShoppingCart cart;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<Address> address;
	
}
