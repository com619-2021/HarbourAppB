package com.devops.groupb.harbourmaster.dto;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.sql.Timestamp;

import javax.persistence.Lob;
import javax.persistence.Basic;
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

	@Column(name="start", columnDefinition="TIMESTAMP")
	private LocalDateTime start;

	@Column(name="end", columnDefinition="TIMESTAMP")
	private LocalDateTime end;

	// Empty default constructor needed for H2 in-memory testing DB
	public Tide() {

	}

	public Tide(int id, double height, LocalDateTime start, LocalDateTime end) {
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

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	@Override
	public String toString() {
		String startString = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		String endString = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		return getClass().getSimpleName() + String.format("[id=%d, height=%f start=%s, end=%s]", id, height, startString, endString);
	}
}
