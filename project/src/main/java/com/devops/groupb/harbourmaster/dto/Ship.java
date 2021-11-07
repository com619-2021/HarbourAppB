package com.devops.groupb.harbourmaster.dto;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ships")
public class Ship {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private ShipType type;
	private double draft;

	// Empty default constructor needed for H2 in-memory testing DB.
	public Ship() {

	}

	// Constructor for saving a Ship without giving an explicit ID.
	public Ship(ShipType type, double draft) {
		this.type = type;
		this.draft = draft;
	}

	public Ship(int id, ShipType type, double draft) {
		this.id = id;
		this.type = type;
		this.draft = draft;
	}

	public Boolean isValid() {
		return (id >= 0) && (draft >= 0.0) && (type.ordinal() >= 0 && type.ordinal() < ShipType.values().length);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return getClass().getSimpleName() + String.format("[id=%d, type=%s, draft=%f]", id, type, draft);
	}
}
