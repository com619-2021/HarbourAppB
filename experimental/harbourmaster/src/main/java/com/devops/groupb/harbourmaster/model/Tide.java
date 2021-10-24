package com.devops.groupb.harbourmaster.model;

public class Tide {
	private int id;
	private String until; /* Date object would be more suitable. */
	private double height;
	private TideStage type;

	public Tide(int id, String until, double height, TideStage type) {
		this.id = id;
		this.until = until;
		this.height = height;
		this.type = type;
	}

	public int get_id() {
		return id;
	}

	public void set_id(int id) {
		this.id = id;
	}

	public String get_until() {
		return until;
	}

	public void set_until(String until) {
		this.until = until;
	}

	public TideStage get_type() {
		return type;
	}

	public void set_type(TideStage type) {
		this.type = type;
	}

	public double get_height() {
		return height;
	}

	public void set_height(double height) {
		this.height = height;
	}
}
