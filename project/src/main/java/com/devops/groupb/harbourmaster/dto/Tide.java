package com.devops.groupb.harbourmaster.dto;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "tides")
public class Tide {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private double height;

	@Column(name="start")
	private Date start;

	@Column(name="end")
	private Date end;
		
	public Tide(int id, double height, Date start, Date end) {
		this.id = id;
		this.height = height;
		this.start = start;
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + String.format("[id=%d, height=%f start=%tc, end=%tc, ]", id, height, start, end);
	}
}
