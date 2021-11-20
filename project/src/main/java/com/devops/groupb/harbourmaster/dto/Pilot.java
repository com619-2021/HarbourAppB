package com.devops.groupb.harbourmaster.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devops.groupb.harbourmaster.dto.TimePeriod;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.CollectionTable;
import javax.persistence.EnumType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "pilots")
public class Pilot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private int pk;

	@GeneratedValue(generator = "UUID")
	@ApiModelProperty(hidden = true)
	private UUID uuid = UUID.randomUUID();

	@ElementCollection
	@CollectionTable(name = "allowedTo", joinColumns = @JoinColumn(name = "pk"))
	@Enumerated(EnumType.STRING)
	private List<ShipType> allowedTo = new ArrayList();
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<DayOfWeek> workingDays;

	@OneToOne(cascade = {CascadeType.ALL})
	private TimePeriod workingTimes;

	/* Empty default constructor needed for Hibernate DB */
	public Pilot() {

	}

	/* Constructor for testing. */
	public Pilot(List<ShipType> allowedTo, String firstName, String lastName, LocalDate dateOfBirth, List<DayOfWeek> workingDays, TimePeriod workingTimes) {
		this.uuid = UUID.randomUUID();
		this.allowedTo = allowedTo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.workingDays = workingDays;
		this.workingTimes = workingTimes;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public List<ShipType> getAllowedTo() {
		return allowedTo;
	}

	public void setAllowedTo(List<ShipType> allowedTo) {
		this.allowedTo = allowedTo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<DayOfWeek> getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(List<DayOfWeek> workingDays) {
		this.workingDays = workingDays;
	}

	public TimePeriod getWorkingTimes() {
		return workingTimes;
	}

	public void setWorkingTimes(TimePeriod workingTimes) {
		this.workingTimes = workingTimes;
	}

	@Override
	public String toString() {
		String dobString = dateOfBirth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String allowedToStr = allowedTo.stream()
			.map(type -> String.valueOf(type))
			.collect(Collectors.joining(", ", "[", "]"));

		return "Pilot [allowedToStr=" + allowedToStr + ", dobString=" + dobString + ", firstName=" + firstName
			+ ", lastName=" + lastName + ", pk=" + pk + ", uuid=" + uuid + ", workingTimes=" + workingTimes
			+ "]";
	}
}
