package com.devops.groupb.harbourmaster.dto;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.OrderStatus;

import java.sql.Timestamp;

import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne
	private Ship ship;

	@OneToOne
	private Pilot pilot;

	@Column(name="orderDate", columnDefinition="TIMESTAMP")
	private LocalDateTime orderDate;

	@Column(name="start", columnDefinition="TIMESTAMP")
	private LocalDateTime start;

	@OneToOne
	private Berth berth;
	private OrderStatus status;

	// Empty default constructor needed for H2 in-memory testing DB.
	public Order() {

	}

	public Order(Ship ship, Pilot pilot, Berth berth) {
		this.ship = ship;
		this.pilot = pilot;
		this.berth = berth;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
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

	@Override
	public String toString() {
		String orderDateString = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		String startString = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		return getClass().getSimpleName() + "[berth=" + berth + ", start=" + startString + ", id=" + id + ", orderDate=" + orderDateString + "pilot=" + pilot + ", ship="
			+ ship + ", status=" + status.name() + "]";
	}
}
