package com.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

@Entity
public class Inventory {

    @Id
    @NotNull(message = "Inventory code is required.")
    private Long code;

    @NotBlank(message = "Item name is required.")
    private String name;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Unit cost is required.")
    @Positive(message = "Cost must be greater than zero.")
    private Double unitCost;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Inventory(Long code, String name, String description, Double unitCost, int quantity) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.unitCost = unitCost;
		this.quantity = quantity;
	}

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

}
