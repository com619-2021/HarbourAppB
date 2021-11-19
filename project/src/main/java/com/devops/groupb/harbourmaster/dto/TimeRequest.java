package com.devops.groupb.harbourmaster.dto;

import java.time.LocalDate;

public class TimeRequest {
	private Ship ship;
	private LocalDate arrivalDate;

	public TimeRequest(Ship ship, LocalDate arrivalDate) {
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
		return "TimeRequest [arrivalDate=" + arrivalDate + ", ship=" + ship + "]";
	}
}
