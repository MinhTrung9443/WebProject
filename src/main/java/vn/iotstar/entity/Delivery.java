package vn.iotstar.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Delivery")
public class Delivery implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int deliveryId;
	private String deliveryName;
	private int price;
	
	@OneToMany(mappedBy = "delivery")
	@JsonManagedReference
	private List<Shipper> shipper;
}
