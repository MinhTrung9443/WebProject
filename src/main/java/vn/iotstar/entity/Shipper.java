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
@Table(name = "Shipper")
public class Shipper extends Person implements Serializable {
	private String address;
	private String deliveryArea;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "deliveryId")
	@JsonBackReference 
	private Delivery delivery;
	
	
	@OneToMany(mappedBy = "shipper")
	@JsonManagedReference
	private List<OrderAssignment> orderAss;
}
