package com.devops.groupb.harbourmaster.dto;

import java.time.LocalDate;

public class TimeRequestWrapper {
	private Ship ship;
	private LocalDate arrivalDate;

	public TimeRequestWrapper(Ship ship, LocalDate arrivalDate) {
		this.ship = ship;
		this.arrivalDate = arrivalDate;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[arrivalDate=" + arrivalDate + ", ship=" + ship + "]";
	}
}
