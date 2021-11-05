package com.devops.groupb.harbourmaster.dto;

import java.util.ArrayList;
import java.util.StringJoiner;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pilots")
public class Pilot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String href = null;
	private ArrayList<ShipType> allowedTo;
	private String firstName;
	private String lastName;
	private String dateOfBirth; // change to Date

	// Empty default constructor needed for H2 in-memory testing DB
	public Pilot() {

	}

	// Constructor for saving a Pilot without giving an explicit ID.
	public Pilot(ArrayList<ShipType> allowedTo, String firstName, String lastName, String dateOfBirth) {
		this.allowedTo = allowedTo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}


	public Pilot(int id, ArrayList<ShipType> allowedTo, String firstName, String lastName, String dateOfBirth) {
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

	public ArrayList<ShipType> getAllowedTo() {
		return allowedTo;
	}

	public void setAllowedTo(ArrayList<ShipType> allowedTo) {
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + String.format("[id=%d, allowedTo=*WIP*, firstName=%s, lastName=%s, dateOfBirth=%s]", id, firstName, lastName, dateOfBirth);
	}
}
