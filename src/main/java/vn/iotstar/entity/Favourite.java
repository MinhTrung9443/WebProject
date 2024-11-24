package vn.iotstar.entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favourite implements  Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long favouriteId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId", referencedColumnName="id")
    @JsonBackReference
    private User user;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="productId")
    @JsonBackReference
    private Product product;
}