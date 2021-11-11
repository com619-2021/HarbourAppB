package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

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
@Table(name="berths")
public class Berth {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	private UUID uuid = null;
	private double lat;
	private double lon;

	// Empty default constructor needed for H2 in-memory testing DB.
	public Berth() {

	}

	// Constructor used for testing; NOT to be called in the main program.
	public Berth(double lat, double lon) {
		this.uuid = UUID.randomUUID();
		this.lat = lat;
		this.lon = lon;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
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
		return getClass().getSimpleName() + "[pk=" + pk + ", uuid=" + uuid + ", lat=" + lat + ", lon=" + lon + "]";
	}
}
