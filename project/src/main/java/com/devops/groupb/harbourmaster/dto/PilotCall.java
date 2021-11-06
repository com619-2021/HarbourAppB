package com.devops.groupb.harbourmaster.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;

@Entity
public class PilotCall {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int id;
	private int xPos;
	private int yPos;

	private int pilotId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getPilotId() {
		return pilotId;
	}

	public void setPilotId(int pilotId) {
		this.pilotId = pilotId;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + id + ", pilotId=" + pilotId + ", xPos=" + xPos + ", yPos=" + yPos + "]";
	}
}
