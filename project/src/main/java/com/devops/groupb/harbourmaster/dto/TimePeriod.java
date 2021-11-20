package com.devops.groupb.harbourmaster.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class TimePeriod {
	@Id
	@ApiModelProperty(hidden = true)
	private int pk;

	private LocalTime start;
	private LocalTime end;

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
