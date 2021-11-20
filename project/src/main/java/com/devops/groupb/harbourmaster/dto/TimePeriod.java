package com.devops.groupb.harbourmaster.dto;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class TimePeriod implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	private LocalTime start;
	private LocalTime end;

	/* Empty default constructor needed for Hibernate DB */
	public TimePeriod() {

	}

	/* Constructor for testing. */
	public TimePeriod(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
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

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[pk=" + pk + ", start=" + start + ", end=" + end + "]";
	}
}
