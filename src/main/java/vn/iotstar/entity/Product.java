package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Product")
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	@Column(columnDefinition = "nvarchar(max)")
	private String productName;
	private int price;
	@Column(columnDefinition = "nvarchar(max)")
	private String description;
	private String brand;
	private Date expirationDate;
	private Date manufactureDate;
	@Column(columnDefinition = "nvarchar(max)")
	private String ingredient;
	@Column(columnDefinition = "nvarchar(max)")
	private String instruction;
	private String volumeOrWeight;
	private String brandOrigin;
	private long stock;
	private String images;
	private LocalDate warehouseDateFirst = LocalDate.now();

	@ManyToOne()
	@JsonBackReference
	@JoinColumn(name = "categoryId")
	private Category category;

	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProductFeedback> feedbacks;
	public void addFeedbacks(ProductFeedback feedback)
	{
		getFeedbacks().add(feedback);
		feedback.setProduct(this);
	}
	public void removeFeedbacks(ProductFeedback feedback)
	{
		getFeedbacks().remove(feedback);
		feedback.setProduct(null);
	}
	@OneToMany(mappedBy= "product",cascade = CascadeType.ALL)
	@JsonBackReference
	private List<CartItem> cartitem;
	
	@OneToMany(mappedBy= "product",cascade = CascadeType.ALL)
	@JsonBackReference
	private List<OrderLine> orderlines;
	

	@OneToMany(mappedBy= "product",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Favourite> favourite;


}
