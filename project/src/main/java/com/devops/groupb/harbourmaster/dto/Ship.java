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
	private String href = null;
	private ShipType type;
	private double draft;

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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
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
