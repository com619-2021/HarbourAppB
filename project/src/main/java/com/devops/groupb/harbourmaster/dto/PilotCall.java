package com.devops.groupb.harbourmaster.dto;

import io.swagger.annotations.ApiModelProperty;

import com.devops.groupb.harbourmaster.dto.Berth;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.CascadeType;

@Entity
public class PilotCall {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int id;
	private int pilotId;

	@OneToOne(cascade = {CascadeType.ALL})
	private Berth berth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPilotId() {
		return pilotId;
	}

	public void setPilotId(int pilotId) {
		this.pilotId = pilotId;
	}

	public Berth getBerth() {
		return berth;
	}

	public void setBerth(Berth berth) {
		this.berth = berth;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + id + ", pilotId=" + pilotId + ", berth=" + berth + "]";
	}
}
