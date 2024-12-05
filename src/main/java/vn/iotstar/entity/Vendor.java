package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Vendor")
public class Vendor extends Person implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int salary;
	private Date startDate;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	private String address;

}
