package com.devops.groupb.harbourmaster.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class GPS {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	@OneToOne(cascade = {CascadeType.ALL})
	private Ship ship;

	@OneToOne(cascade = {CascadeType.ALL})
	private WaitingLocation location;

	// Empty default constructor needed for H2 in-memory testing DB.
	public GPS() {

	}

	public GPS(Ship ship, WaitingLocation location) {
		this.ship = ship;
		this.location = location;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public WaitingLocation getLocation() {
		return location;
	}

	public void setLocation(WaitingLocation location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "GPS [pk=" + pk + ", location=" + location + ", ship=" + ship + "]";
	}
}
