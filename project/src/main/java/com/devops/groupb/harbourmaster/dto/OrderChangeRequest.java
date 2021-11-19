package com.devops.groupb.harbourmaster.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.devops.groupb.harbourmaster.dto.Order;

@Entity
public class OrderChangeRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pk;

	@OneToOne
	private Order order;
	@Column(name="requestDate", columnDefinition="TIMESTAMP")
	private LocalDateTime requestDate;
	private String reason;
	private OrderChangeStatus status;

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public OrderChangeStatus getStatus() {
		return status;
	}

	public void setStatus(OrderChangeStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		String dateString = requestDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		return getClass().getSimpleName() + "[pk=" + pk + ", order=" + order + ", reason=" + reason + ", requestDate="
			+ dateString + ", status=" + status.name() + "]";
	}
}
