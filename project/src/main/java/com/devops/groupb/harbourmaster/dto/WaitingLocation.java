package com.devops.groupb.harbourmaster.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
public class WaitingLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int id;

	private double lat;
	private double lon;

	// Empty default constructor needed for H2 in-memory testing DB.
	public WaitingLocation() {

	}

	public WaitingLocation(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return getClass().getSimpleName() + "[id=" + id + ", lat=" + lat + ", lon=" + lon + "]";
	}
}
