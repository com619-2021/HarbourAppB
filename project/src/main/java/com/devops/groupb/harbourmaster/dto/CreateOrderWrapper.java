package com.devops.groupb.harbourmaster.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.Ship;

public class CreateOrderWrapper {
	private LocalDate dayOfArrival;
	private Ship ship;
	private Berth berth;

	// Empty default constructor needed for H2 in-memory testing DB.
	public CreateOrderWrapper() {

	}

	public CreateOrderWrapper(Ship ship, LocalDate dayOfArrival, Berth berth) {
		this.ship = ship;
		this.dayOfArrival = dayOfArrival;
		this.berth = berth;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public LocalDate getDayOfArrival() {
		return dayOfArrival;
	}

	public void setDayOfArrival(LocalDate dayOfArrival) {
		this.dayOfArrival = dayOfArrival;
	}

	public Berth getBerth() {
		return berth;
	}

	public void setBerth(Berth berth) {
		this.berth = berth;
	}

	@Override
	public String toString() {
		String dayOfArrivalString = dayOfArrival.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		return getClass().getSimpleName() + "[dayOfArrival=" + dayOfArrivalString + ", ship=" + ship + "]";
	}
}
