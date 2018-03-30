package de.adesso.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Double costPerMonth;
	private Double overdraft;
	private Double overdraftInterest;
	
	
	public Double getOverdraftInterest() {
		return overdraftInterest;
	}
	
	public void setOverdraftInterest(Double overdraftInterest) {
		this.overdraftInterest = overdraftInterest;
	}
	
	public Double getOverdraft() {
		return overdraft;
	}
	
	public void setOverdraft(Double overdraft) {
		this.overdraft = overdraft;
	}
	
	public Double getCostPerMonth() {
		return costPerMonth;
	}
	public void setCostPerMonth(Double costPerMonth) {
		this.costPerMonth = costPerMonth;
	}

}
