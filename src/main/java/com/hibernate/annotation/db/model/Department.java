package com.hibernate.annotation.db.model;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="DEPARTMENT")
@Entity
@NamedQueries({
	@NamedQuery(name = "FIND_ALL_DEPARTMENT", query = "FROM Department"),
	@NamedQuery(name = "FIND_BY_NAME_DEPARTMENT", query = "FROM Department WHERE name =:NAME"),
	@NamedQuery(name = "FIND_BY_PHONE_NUMBER_DEPARTMENT", query = "FROM Department WHERE phoneNumber LIKE(:PHONE_NUMBER)")
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "VIEW_DEPARTMENT", query = "SELECT * FROM DEPARTMENT", resultClass = Department.class),
	@NamedNativeQuery(name = "VIEW_FIND_BY_NAME_DEPARTMENT", query = "SELECT * FROM DEPARTMENT WHERE NAME =:NAME", resultClass = Department.class),
	@NamedNativeQuery(name = "VIEW_FIND_BY_PHONE_NUMBER_DEPARTMENT", query = "SELECT * FROM DEPARTMENT WHERE PHONE_NUMBER LIKE(:PHONE_NUMBER)", resultClass = Department.class) 
})
public class Department extends AbstractEntity{

	@Column(name= "PHONE_NUMBER")
	private String phoneNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy = "department")
	private List<Employee> employees;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
