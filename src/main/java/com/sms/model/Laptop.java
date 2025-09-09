package com.sms.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "LAPTOPS")
public class Laptop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "STATUS")
	private String status;

	/// Replace old ManyToMany students field with OneToMany LaptopHistory

	@OneToMany(mappedBy = "laptop", cascade = CascadeType.ALL)
	private Set<LaptopHistory> laptopHistories;

	// constructors

	public Laptop() {
		super();
	}

	public Laptop(String serialNumber, String model, String status, Set<LaptopHistory> laptopHistories) {
		super();
		this.serialNumber = serialNumber;
		this.model = model;
		this.status = status;
		this.laptopHistories = laptopHistories;
	}

	public Laptop(Long id, String serialNumber, String model, String status, Set<LaptopHistory> laptopHistories) {
		super();
		this.id = id;
		this.serialNumber = serialNumber;
		this.model = model;
		this.status = status;
		this.laptopHistories = laptopHistories;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<LaptopHistory> getLaptopHistories() {
		return laptopHistories;
	}

	public void setLaptopHistories(Set<LaptopHistory> laptopHistories) {
		this.laptopHistories = laptopHistories;
	}
}
