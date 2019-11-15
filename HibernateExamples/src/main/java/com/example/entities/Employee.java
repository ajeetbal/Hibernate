package com.example.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@NamedQuery(name = "Employee.byId", query = "from Employee where id=?")
@NamedNativeQuery(name = "Employee.byName", query = "select * from employee where name=?", resultClass = Employee.class)
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	@Basic(optional = true)
	private int age;

//	@Transient // tells hibernate to ignore field
//	@Basic(fetch = FetchType.LAZY) // LOAD CHILD DATA WHEN NEEDED

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "city", column = @Column(name = "home_city")),
			@AttributeOverride(name = "state", column = @Column(name = "home_state")),
			@AttributeOverride(name = "country", column = @Column(name = "home_country")) })
	private Address homeAddress;
	@Embedded
	private Address officeAddress;

	@Temporal(TemporalType.DATE) // saves only date
	private Date createdOn;

	@Lob // tells hibernate that the field large text .2 type of lob ,char lob and byte
			// lob incase of String hibernate chose clob to store data and in case of byte
			// it uses byte lob
	private String description;

	@OneToOne(cascade = CascadeType.ALL)
	private Vehicle vehicle;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "emp_pay", joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "payment_id"))
	private Collection<Payment> payments = new ArrayList<Payment>();

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "employees")
	private Collection<Project> projects = new ArrayList<Project>();

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Collection<Project> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", homeAddress=" + homeAddress
				+ ", officeAddress=" + officeAddress + ", createdOn=" + createdOn + ", description=" + description
				+ "]";
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Collection<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Collection<Payment> payments) {
		this.payments = payments;
	}

}
