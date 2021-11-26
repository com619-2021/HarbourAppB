package com.devops.groupb.harbourmaster.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tides")
public class Tide {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk;
	private double height;

	@Enumerated(EnumType.ORDINAL)
	private DayOfWeek day;

	@Column(name="start", columnDefinition="time")
	private LocalTime start;

	@Column(name="end", columnDefinition="time")
	private LocalTime end;

	// Empty default constructor needed for H2 in-memory testing DB
	public Tide() {

	}

	public Tide(DayOfWeek day, double height, LocalTime start, LocalTime end) {
		this.day = day;
		this.height = height;
		this.start = start;
		this.end = end;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + String.format("[pk=%d, height=%f, day=%s, start=%s, end=%s]", pk, height, day.name(), start.toString(), end.toString());
	}
}
