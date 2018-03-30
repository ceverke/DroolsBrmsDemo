package de.adesso.blog.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PrivateCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String lastName;
	private String firstName;
	private Date birthDate;
	private JobState jobState;
	private Double monthlySalaray;
	private boolean nonOnlineTransferals;
	private boolean secondCard;
	private boolean goldCard;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
	private BankAccount bankAccount;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public JobState getJobState() {
		return jobState;
	}

	public void setJobState(JobState jobState) {
		this.jobState = jobState;
	}

	public Double getMonthlySalaray() {
		return monthlySalaray;
	}

	public void setMonthlySalaray(Double monthlySalaray) {
		this.monthlySalaray = monthlySalaray;
	}

	public boolean isNonOnlineTransferals() {
		return nonOnlineTransferals;
	}

	public void setNonOnlineTransferals(boolean nonOnlineTransferals) {
		this.nonOnlineTransferals = nonOnlineTransferals;
	}

	public boolean isSecondCard() {
		return secondCard;
	}

	public void setSecondCard(boolean secondCard) {
		this.secondCard = secondCard;
	}

	public boolean isGoldCard() {
		return goldCard;
	}

	public void setGoldCard(boolean goldCard) {
		this.goldCard = goldCard;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public int calculateAge() {
		SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate birthDate = LocalDate.parse(birthDayFormat.format(this.getBirthDate()));
		LocalDate curDate = LocalDate.now();
		return Period.between(birthDate, curDate).getYears();		
	}

}
