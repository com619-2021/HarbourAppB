package com.devops.groupb.harbourmaster.dto;

import com.devops.groupb.harbourmaster.dto.Ship;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PilotBookingRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int id;

	@OneToOne
	private Ship ship;

	@Column(name="date", columnDefinition="TIMESTAMP")
	private LocalDate date;

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[date=" + date + ", id=" + id + ", ship=" + ship + "]";
	}
}
