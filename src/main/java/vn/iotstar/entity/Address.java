package vn.iotstar.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Address")
public class Address implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressId;
	private String addressDetail;
	private String addressType;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	@JsonBackReference
	private User user;
}
