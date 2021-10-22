package com.devops.groupb.harbourmaster.model;

public class Ship {
	private int id; 
	private int length;
	private int width;
	private int weight;

    public Ship(int id, int length, int width, int weight) {
        this.id = id;
        this.length = length;
        this.width = width;
        this.weight = weight;
    }

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + id + ",length=" + length + ",width=" + width + ",weight=" + weight + "]";
	}

	public Boolean is_valid() {
		return id > 0 && length > 0 && width > 0 && weight > 0;
	}
	
    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public int get_length() {
        return length;
    }

    public void set_length(int length) {
        this.length = length;
    }

    public int get_width() {
        return width;
    }

    public void set_width(int width) {
        this.width = width;
    }
	
    public int get_weight() {
        return weight;
    }

    public void set_weight(int weight) {
        this.weight = weight;
    }
}
