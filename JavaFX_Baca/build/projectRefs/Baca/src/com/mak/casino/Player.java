package com.mak.casino;

import java.util.Date;

public class Player {
	
	private String firstName;
	
	private String lastName;
	
	private Date dob;
	
	private double unitBalance;
	
	public Player(String firstName, String lastName, Date dob, double unitBalance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.unitBalance = unitBalance;
	}
	
	public Player(String firstName, double unitBalance){
		this.firstName = firstName;
		this.unitBalance = unitBalance;
	}
	
	public Player(){
		
	}
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public double getUnitBalance() {
		return unitBalance;
	}

	public void setUnitBalance(double unitBalance) {
		this.unitBalance = unitBalance;
	}
	

}
