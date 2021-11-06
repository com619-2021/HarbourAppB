package com.devops.groupb.harbourmaster.dto;

import com.devops.groupb.harbourmaster.dto.Ship;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class PilotBookingRequest {
	private Ship ship;
	private LocalDate date;

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
		String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		return getClass().getSimpleName() + "[date=" + dateString + ", ship=" + ship + "]";
	}
}
