package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devops.groupb.harbourmaster.dto.OrderStatus;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="receipts")
public class Receipt {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	private UUID receiptUUID;

	private OrderStatus initialStatus;

	@Column(name="dateOfOrder", columnDefinition="TIMESTAMP")
	private LocalDateTime dateOfOrder;

	@Column(name="allocatedStart", columnDefinition="TIMESTAMP")
	private LocalDateTime allocatedStart;

	@Column(name="allocatedEnd", columnDefinition="TIMESTAMP")
	private LocalDateTime allocatedEnd;

	private double fare;

	private UUID orderUUID;
	private UUID shipUUID;
	private UUID berthUUID;

	private UUID pilotUUID;
	private String pilotFirstName;
	private String pilotLastName;

	/* Empty default constructor needed for Hibernate DB */
	public Receipt() {

	}

	public Receipt(OrderStatus initialStatus, LocalDateTime dateOfOrder, LocalDateTime allocatedStart, LocalDateTime allocatedEnd, double fare, UUID orderUUID, UUID shipUUID, UUID berthUUID, UUID pilotUUID, String pilotFirstName, String pilotLastName) {
		this.receiptUUID = UUID.randomUUID();
		this.initialStatus = initialStatus;
		this.dateOfOrder = dateOfOrder;
		this.allocatedStart = allocatedStart;
		this.allocatedEnd = allocatedEnd;
		this.fare = fare;
		this.orderUUID = orderUUID;
		this.shipUUID = shipUUID;
		this.berthUUID = berthUUID;
		this.pilotUUID = pilotUUID;
		this.pilotFirstName = pilotFirstName;
		this.pilotLastName = pilotLastName;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public UUID getReceiptUUID() {
		return receiptUUID;
	}

	public void setReceiptUUID(UUID receiptUUID) {
		this.receiptUUID = receiptUUID;
	}

	public OrderStatus getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(OrderStatus initialStatus) {
		this.initialStatus = initialStatus;
	}

	public LocalDateTime getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(LocalDateTime dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public LocalDateTime getAllocatedStart() {
		return allocatedStart;
	}

	public void setAllocatedStart(LocalDateTime allocatedStart) {
		this.allocatedStart = allocatedStart;
	}

	public LocalDateTime getAllocatedEnd() {
		return allocatedEnd;
	}

	public void setAllocatedEnd(LocalDateTime allocatedEnd) {
		this.allocatedEnd = allocatedEnd;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public UUID getOrderUUID() {
		return orderUUID;
	}

	public void setOrderUUID(UUID orderUUID) {
		this.orderUUID = orderUUID;
	}

	public UUID getShipUUID() {
		return shipUUID;
	}

	public void setShipUUID(UUID shipUUID) {
		this.shipUUID = shipUUID;
	}

	public UUID getBerthUUID() {
		return berthUUID;
	}

	public void setBerthUUID(UUID berthUUID) {
		this.berthUUID = berthUUID;
	}

	public UUID getPilotUUID() {
		return pilotUUID;
	}

	public void setPilotUUID(UUID pilotUUID) {
		this.pilotUUID = pilotUUID;
	}

	public String getPilotFirstName() {
		return pilotFirstName;
	}

	public void setPilotFirstName(String pilotFirstName) {
		this.pilotFirstName = pilotFirstName;
	}

	public String getPilotLastName() {
		return pilotLastName;
	}

	public void setPilotLastName(String pilotLastName) {
		this.pilotLastName = pilotLastName;
	}
}
