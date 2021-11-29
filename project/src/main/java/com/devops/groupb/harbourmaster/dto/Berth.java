package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="berths")
public class Berth {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	private UUID uuid = null;
	private double latitude;
	private double longitude;

	// Empty default constructor needed for H2 in-memory testing DB.
	public Berth() {

	}

	// Constructor used for testing; NOT to be called in the main program.
	public Berth(double latitude, double longitude) {
		this.uuid = UUID.randomUUID();
		this.latitude = latitude;
		this.longitude = longitude;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[pk=" + pk + ", uuid=" + uuid + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
