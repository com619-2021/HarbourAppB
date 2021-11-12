package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

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
	private int pk;
	private UUID pilotUUID;
	private ShipType shipType;

	@OneToOne(cascade = {CascadeType.ALL})
	private Berth berth;

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public UUID getPilotUUID() {
		return pilotUUID;
	}

	public void setPilotUUID(UUID pilotUUID) {
		this.pilotUUID = pilotUUID;
	}

	public Berth getBerth() {
		return berth;
	}

	public void setBerth(Berth berth) {
		this.berth = berth;
	}
	
	public ShipType getShipType() {
		return shipType;
	}

	public void setShipType(ShipType shipType) {
		this.shipType = shipType;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[pk=" + pk + ", pilotUUID=" + pilotUUID + ", berth=" + berth + "]";
	}
}
