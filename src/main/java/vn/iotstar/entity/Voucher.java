package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
	@Column(columnDefinition = "nvarchar(max)")
	private String voucherCode;
	private String voucherType;
	private int voucherValue;
	private Date startDate;
	private Date endDate;
	private int active;
	
	@OneToOne()
	@JoinColumn(name = "orderId")
	@JsonBackReference
	private Order order;
}
