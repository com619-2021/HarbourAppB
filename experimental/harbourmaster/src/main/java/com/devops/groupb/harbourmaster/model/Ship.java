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
		return id > 0 && (type == ShipType.PASSENGER || type == ShipType.CARGO);
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
