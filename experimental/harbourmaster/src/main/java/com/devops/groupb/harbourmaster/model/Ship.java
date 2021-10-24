package com.devops.groupb.harbourmaster.model;

public class Ship {
	private int id;
	private ShipType type;
	private double minimum_tide_height;

	public Ship(int id, ShipType type, double minimum_tide_height) {
		this.id = id;
		this.type = type;
		this.minimum_tide_height = minimum_tide_height;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + id + ",type=" + type + ",minimum_tide_height=" + minimum_tide_height + "]";
	}

	public Boolean is_valid() {
		//Changed this to be slightly more mathematically based so that if more ship types are added in the future.
		//They will be automatically included in the check without changes needing to be made. - Caitlyn
		return (id >= 0) && (minimum_tide_height >= 0.0) && (type.ordinal() >= 0 && type.ordinal() < ShipType.values().length);
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

	public double get_minimum_tide_height() {
		return minimum_tide_height;
	}

	public void set_minimum_tide_height(double minimum_tide_height) {
		this.minimum_tide_height = minimum_tide_height;
	}
}
