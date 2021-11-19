package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="ships")
public class Ship {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	private UUID uuid = null;
	private ShipType type;
	private double draft;

	// Empty default constructor needed for H2 in-memory testing DB.
	public Ship() {

	}

	// Constructor used for testing; NOT to be called in the main program.
	public Ship(ShipType type, double draft) {
		this.uuid = UUID.randomUUID();
		this.type = type;
		this.draft = draft;
	}

	public Boolean isValid() {
		return (pk >= 0) && (draft >= 0.0) && (type.ordinal() >= 0 && type.ordinal() < ShipType.values().length);
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public ShipType getType() {
		return type;
	}

	public void setType(ShipType type) {
		this.type = type;
	}

	public double getDraft() {
		return draft;
	}

	public void setDraft(double draft) {
		this.draft = draft;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + String.format("[pk=%d, uuid=%s, type=%s, draft=%f]", pk, uuid, type, draft);
	}
}
