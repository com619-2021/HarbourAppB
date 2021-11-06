package com.devops.groupb.harbourmaster.dto;

import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="berths")
public class Berth {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private double xPos;
	private double yPos;

	// Empty default constructor needed for H2 in-memory testing DB.
	public Berth() {

	}

	public Berth(int id, double xPos, double yPos) {
		this.id = id;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + id + ", xPos=" + xPos + ", yPos=" + yPos + "]";
	}
}
