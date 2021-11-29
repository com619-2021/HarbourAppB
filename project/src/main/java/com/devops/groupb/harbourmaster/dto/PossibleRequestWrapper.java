package com.devops.groupb.harbourmaster.dto;

import java.time.LocalDate;

public class PossibleRequestWrapper {
	private Ship ship;
	private LocalDate dayOfArrival;

	public PossibleRequestWrapper(Ship ship, LocalDate dayOfArrival) {
		this.ship = ship;
		this.dayOfArrival = dayOfArrival;
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

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[dayOfArrival=" + dayOfArrival + ", ship=" + ship + "]";
	}
}
