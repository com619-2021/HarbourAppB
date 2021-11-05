package com.devops.groupb.harbourmaster.dto;

import java.util.List;

import java.util.ArrayList;
import java.util.StringJoiner;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "pilots")
public class Pilot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String href = null;

	@ElementCollection
	@CollectionTable(name = "allowedTo", joinColumns = @JoinColumn(name = "id"))
	@Enumerated(EnumType.STRING)
	private List<ShipType> allowedTo = new ArrayList();
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	// Empty default constructor needed for H2 in-memory testing DB.
	public Pilot() {

	}

	// Constructor for saving a Pilot without giving an explicit ID.
	public Pilot(List<ShipType> allowedTo, String firstName, String lastName, LocalDate dateOfBirth) {
		this.allowedTo = allowedTo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public Pilot(int id, List<ShipType> allowedTo, String firstName, String lastName, LocalDate dateOfBirth) {
		this.id = id;
		this.allowedTo = allowedTo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		String dobString = dateOfBirth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		return getClass().getSimpleName() + String.format("[id=%d, allowedTo=*WIP*, firstName=%s, lastName=%s, dateOfBirth=%s]", id, firstName, lastName, dobString);
	}
}
