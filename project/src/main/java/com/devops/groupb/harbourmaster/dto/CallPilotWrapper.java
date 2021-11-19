package com.devops.groupb.harbourmaster.dto;

import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.ShipType;

public class CallPilotWrapper {
	private ShipType shipType;
	private Berth berth;

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
		return this.getClass().getSimpleName() + "[berth=" + berth + ", shipType=" + shipType + "]";
	}
}
