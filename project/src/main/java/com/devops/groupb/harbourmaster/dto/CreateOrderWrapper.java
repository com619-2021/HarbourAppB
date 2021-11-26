package com.devops.groupb.harbourmaster.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Ship;

public class CreateOrderWrapper {
	private LocalDate date;
	private Ship ship;
	private Berth berth;

	// Empty default constructor needed for H2 in-memory testing DB.
	public CreateOrderWrapper() {

	}

	public CreateOrderWrapper(Ship ship, LocalDate date, Berth berth) {
		this.ship = ship;
		this.date = date;
		this.berth = berth;
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

	public Berth getBerth() {
		return berth;
	}

	public void setBerth(Berth berth) {
		this.berth = berth;
	}

	@Override
	public String toString() {
		String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		return getClass().getSimpleName() + "[date=" + dateString + ", ship=" + ship + "]";
	}
}
