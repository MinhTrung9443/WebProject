package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
	private int productId;
	private String productName;
	private int price;
	private String description;
	private String brand;
	private Date expirationdate;
	private Date manufactureDate;
	private String ingredient;
	private String instruction;
	private String volumeOrWeight;
	private String origin;
	private String brandOrigin;
	private String images;
	private Date warehouseDateFirst;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonBackReference
	@JoinColumn(name = "categoryId")
	private Category category;

	@OneToMany(mappedBy = "product")
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
	@OneToOne(mappedBy = "product")
	@JsonBackReference
	private CartItem cartitem;
	@OneToOne(mappedBy = "product")
	@JsonBackReference
	private OrderLine orderline;
	
	@OneToMany(mappedBy= "product")
	@JsonManagedReference
	private List<Favourite> favourite;


}
