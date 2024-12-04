package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Voucher")
public class Voucher implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int voucherId;
	
	@Column( columnDefinition = "NVARCHAR(255)")
	private String voucherType;
	
	private String voucherCode;
	private int voucherValue;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int active;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "orderId")
	@JsonBackReference
	private Order order;
}
