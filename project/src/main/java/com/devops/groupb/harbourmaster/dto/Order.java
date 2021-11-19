package com.devops.groupb.harbourmaster.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	@GeneratedValue(generator = "UUID")
	private UUID uuid;

	private OrderStatus status;

	private String reason;

	private double fare;

	@Column(name="orderDate", columnDefinition="TIMESTAMP")
	private LocalDateTime orderDate;

	@Column(name="requestedDate", columnDefinition="TIMESTAMP")
	private LocalDate requestedDate;

	@Column(name="allocatedTime", columnDefinition="TIMESTAMP")
	private LocalDateTime allocatedTime;

	@OneToOne(cascade = {CascadeType.ALL})
	private Ship ship;

	@OneToOne(cascade = {CascadeType.ALL})
	private Pilot pilot;

	@OneToOne(cascade = {CascadeType.ALL})
	private Berth berth;

	@Transient
	private final double[] fares = { 311.85, 589.23, 189.32 };

	// Empty default constructor needed for H2 in-memory testing DB.
	public Order() {

	}

	public Order(Ship ship, Berth berth, LocalDate requestedDate) {
		this.uuid = UUID.randomUUID();
		this.ship = ship;
		this.berth = berth;
		this.requestedDate = requestedDate;
		orderDate = LocalDateTime.now();
		fare = fares[ship.getType().ordinal()];
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getAllocatedTime() {
		return allocatedTime;
	}

	public void setAllocatedTime(LocalDateTime allocatedTime) {
		this.allocatedTime = allocatedTime;
	}

	public Berth getBerth() {
		return berth;
	}

	public void setBerth(Berth berth) {
		this.berth = berth;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	@Override
	public String toString() {
		String orderDateString = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		String requestedDateString = requestedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String allocatedTimeString = "";

		if (allocatedTime != null) {
			allocatedTimeString = allocatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		}

		return "Order [allocatedTime=" + allocatedTimeString + ", berth=" + berth + ", pk=" + pk
			+ ", uuid=" + uuid + ", orderDate=" + orderDateString + ", pilot=" + pilot + ", requestedDate=" + requestedDateString
			+ ", ship=" + ship + ", status=" + status.name() + ", reason=" + reason + ", fare=" + fare + "]";
	}
}
