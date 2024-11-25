package vn.iotstar.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Category")
public class Category {
	@Id
	
	private int categoryId;
	private String categoryName;
	
	@OneToMany(mappedBy = "category")
	@JsonManagedReference
	private List<Product> product;
}
