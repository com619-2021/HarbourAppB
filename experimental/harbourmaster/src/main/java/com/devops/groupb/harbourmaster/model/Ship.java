package com.devops.groupb.harbourmaster.model;

public class Ship {
	private int id;
	private ShipType type;

	public Ship(int id, ShipType type) {
		this.id = id;
		this.type = type;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + id + ",type=" + type + "]";
	}

	public Boolean is_valid() {
            //Changed this to be slightly more mathematically based so that if more ship types are added in the future.
            //They will be automatically included in the check without changes needing to be made. - Caitlyn
		return (id >= 0) && (type.ordinal() >= 0 && type.ordinal() < ShipType.values().length);
	}

	public int get_id() {
		return id;
	}

	public void set_id(int id) {
		this.id = id;
	}

	public ShipType get_type() {
		return type;
	}

	public void set_type(ShipType type) {
		this.type = type;
	}
}
