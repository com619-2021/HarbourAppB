package com.devops.groupb.harbourmaster.model;

public class Pilot {
	private int id; 
	private int training_level;

	public Pilot() {

    }

    public Pilot(int id, int training_level) {
        this.id = id;
        this.training_level = training_level;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public int get_training_level() {
        return training_level;
    }

    public void set_training_level(int training_level) {
        this.training_level = training_level;
    }
}
