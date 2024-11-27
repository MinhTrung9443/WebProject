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
	@Column(columnDefinition = "nvarchar(max)")
	private String categoryName;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Product> product;
}
