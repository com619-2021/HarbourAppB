package com.devops.groupb.harbourmaster.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class WaitingLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private int pk;

	private double lat;
	private double lon;

	// Empty default constructor needed for H2 in-memory testing DB.
	public WaitingLocation() {

	}

	public WaitingLocation(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[pk=" + pk + ", lat=" + lat + ", lon=" + lon + "]";
	}
}
