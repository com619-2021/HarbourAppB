package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.PilotBookingRequest;
import com.devops.groupb.harbourmaster.dto.Ship;

public class OrderChangeRequestWrapper {
	private UUID parentUUID;
	private String reason;
	private PilotBookingRequest newRequest;

	public UUID getParentUUID() {
		return parentUUID;
	}

	public void setParentUUID(UUID parentUUID) {
		this.parentUUID = parentUUID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public PilotBookingRequest getNewRequest() {
		return newRequest;
	}

	public void setNewRequest(PilotBookingRequest newRequest) {
		this.newRequest = newRequest;
	}

	@Override
	public String toString() {
		return "OrderChangeRequestWrapper [newRequest=" + newRequest + ", parentUUID=" + parentUUID + ", reason=" + reason + "]";
	}
}
