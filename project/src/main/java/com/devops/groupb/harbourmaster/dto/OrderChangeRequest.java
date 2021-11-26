package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.OrderChangeRequestStatus;

@Entity
public class OrderChangeRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pk;

	private UUID uuid;

	private UUID parentUUID;

	@Column(name="changeRequestDate", columnDefinition="TIMESTAMP")
	private LocalDateTime changeRequestDate;

	@Column(name="requestedDate", columnDefinition="TIMESTAMP")
	private LocalDate requestedDate;
	private String reason;
	private OrderChangeRequestStatus status;

	@OneToOne(cascade = {CascadeType.ALL})
	private Ship ship;

	@OneToOne(cascade = {CascadeType.ALL})
	private Berth berth;

	/* Empty default constructor needed for Hibernate DB */
	public OrderChangeRequest() {

	}

	public OrderChangeRequest(Ship ship, Berth berth, LocalDate requestedDate) {
		this.uuid = UUID.randomUUID();
		this.ship = ship;
		this.berth = berth;
		this.requestedDate = requestedDate;
		changeRequestDate = LocalDateTime.now();
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public OrderChangeRequestStatus getStatus() {
		return status;
	}

	public void setStatus(OrderChangeRequestStatus status) {
		this.status = status;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getParentUUID() {
		return parentUUID;
	}

	public void setParentUUID(UUID parentUUID) {
		this.parentUUID = parentUUID;
	}

	public LocalDateTime getChangeRequestDate() {
		return changeRequestDate;
	}

	public void setChangeRequestDate(LocalDateTime changeRequestDate) {
		this.changeRequestDate = changeRequestDate;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Berth getBerth() {
		return berth;
	}

	public void setBerth(Berth berth) {
		this.berth = berth;
	}

	@Override
	public String toString() {
		String dateString = requestedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		return getClass().getSimpleName() + "[pk=" + pk + ", parentUUID=" + parentUUID + ", uuid=" + uuid + ", ship=" + ship + ", berth=" + berth + ", reason=" + reason + ", requestedDate="
			+ dateString + ", status=" + status.name() + "]";
	}
}
