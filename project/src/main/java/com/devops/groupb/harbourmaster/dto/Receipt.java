package com.devops.groupb.harbourmaster.dto;

import java.util.UUID;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devops.groupb.harbourmaster.dto.OrderStatus;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "receipts")
public class Receipt {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	private double totalPrice;

	/* order uuid, not receipt uuid. */
	private UUID uuid;

	/* Empty default constructor needed for Hibernate DB */
	public Receipt() {

	}

	public Receipt(double totalPrice, UUID uuid) {
		this.totalPrice = totalPrice;
		this.uuid = uuid;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Receipt [pk=" + pk + ", totalPrice=" + totalPrice + ", uuid=" + uuid + "]";
	}
}
