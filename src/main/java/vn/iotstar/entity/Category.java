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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	
	@Column(name = "category_name", columnDefinition = "NVARCHAR(255)")
	private String categoryName;
	private int active;
	
	@OneToMany(mappedBy = "category")
	@JsonManagedReference
	private List<Product> product;
}
