package com.hibernate.annotation.db.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "EMPLOYEE")
@Entity
@NamedQueries({
		@NamedQuery(name = "FIND_ALL_EMPLOYEE", query = "FROM Employee"),
		@NamedQuery(name = "FIND_BY_NAME_EMPLOYEE", query = "FROM Employee WHERE name =:NAME"),
		@NamedQuery(name = "FIND_BY_NAME_AND_EMAIL_EMPLOYEE", query = "FROM Employee WHERE name =:NAME AND email =:EMAIL")
})
@NamedNativeQueries({
		@NamedNativeQuery(name = "VIEW_EMPLOYEE", query = "SELECT * FROM EMPLOYEE", resultClass = Employee.class),
		@NamedNativeQuery(name = "VIEW_FIND_BY_NAME_EMPLOYEE", query = "SELECT * FROM EMPLOYEE WHERE NAME =:NAME", resultClass = Employee.class),
		@NamedNativeQuery(name = "VIEW_FIND_BY_NAME_AND_EMAIL_EMPLOYEE", query = "SELECT * FROM EMPLOYEE WHERE NAME =:NAME AND EMAIL =:EMAIL", resultClass = Employee.class) 
})
public class Employee extends AbstractEntity {

	@Column(name = "EMAIL")
	String email;

	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.DATE)
	// Temporal จะกำหนด type Data
	Date createTime;

	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	Department department;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
