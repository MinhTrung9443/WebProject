package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ProductFeedback")
public class ProductFeedback implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int feedbackId;
	@Column(columnDefinition = "nvarchar(max)")
	private String comment;
	private Date reviewDate;
	private int rating;
	
	@ManyToOne()
	@JoinColumn(name = "productId")
	@JsonManagedReference
	private Product product;
}
