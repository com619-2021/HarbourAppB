package com.devops.groupb.harbourmaster.model;

import java.util.ArrayList;

public class Pilot {
	private int id;
	private ArrayList<ShipType> allowed_to;

	public Pilot(int id, ArrayList<ShipType> allowed_to) {
		this.id = id;
		this.allowed_to = allowed_to;
	}

	public int get_id() {
		return id;
	}

	public void set_id(int id) {
		this.id = id;
	}

	public ArrayList<ShipType> get_allowed_to() {
		return allowed_to;
	}

	public void set_allowed_to(ArrayList<ShipType> allowed_to) {
		this.allowed_to = allowed_to;
	}
}
