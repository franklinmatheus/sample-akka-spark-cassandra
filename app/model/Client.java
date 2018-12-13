package model;

import com.datastax.driver.mapping.annotations.Column;

import java.io.Serializable;

public class Client implements Serializable {

	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private Integer address;

	@Column(name = "hascar")
	private Boolean hascar;

	@Column(name = "brokecar")
	private Boolean brokecar;

	@Column(name = "balance")
	private Double balance;

	@Column(name = "hasjob")
	private Boolean hasjob;

	@Column(name = "limit_credit")
	private Double limit_credit;

	@Column(name = "salary")
	private Double salary;

	public Client() {
	}
	
	public Client(Integer id, String name, Integer address, Boolean hascar, Boolean brokecar, Double balance, Boolean hasjob,
			Double limit_credit, Double salary) {
		super();
		this.name = name;
		this.address = address;
		this.hascar = hascar;
		this.brokecar = brokecar;
		this.balance = balance;
		this.hasjob = hasjob;
		this.limit_credit = limit_credit;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}

	public Boolean getHascar() {
		return hascar;
	}

	public void setHascar(Boolean hascar) {
		this.hascar = hascar;
	}

	public boolean isBrokecar() {
		return brokecar;
	}

	public void setBrokecar(boolean brokecar) {
		this.brokecar = brokecar;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean getHasjob() {
		return hasjob;
	}

	public void setHasjob(Boolean hasjob) {
		this.hasjob = hasjob;
	}

	public Double getLimitCredit() {
		return this.limit_credit;
	}

	public void setLimitCredit(Double limit_credit) {
		this.limit_credit = limit_credit;
	}

	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
