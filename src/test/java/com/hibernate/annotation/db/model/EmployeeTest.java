package com.hibernate.annotation.db.model;


import static org.junit.Assert.*;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javassist.expr.NewArray;

import javax.persistence.NamedQueries;

public class EmployeeTest {

	private static final SessionFactory sessionFactory = createSessionFactory();
	
	private Session session;
	
	@Before
	public void setUp(){
		session = sessionFactory.openSession();
	}
	
	@After
	public void tearDown(){
		session.close();
	}
	
	@Test
	public void testCreateEntity(){
		session.getTransaction().begin();
		Employee employee = new Employee();
		employee.setName("Bank");
		employee.setEmail("Bank@gmail.com");
		employee.setCreateTime(new Date());
		session.save(employee);
		session.getTransaction().commit();
		
	}
	
	@Test
	public void testUpdateEntity(){
		session.getTransaction().begin();
		Employee employee = (Employee)session.get(Employee.class,1);
		employee.setName("Bank007");
		session.update(employee);
		session.getTransaction().commit();
	}
	
	@Test
	public void testDeleteEntity(){
		session.getTransaction().begin();
		Employee employee = (Employee)session.get(Employee.class, 1);
		session.delete(employee);
		session.getTransaction().commit();
	}
	
	@Test	//การ Query แบบ  Dynamic Queries
	public void testDynamicQueryFindAllEmployee(){
		Query query = session.createQuery("FROM Employee");
		List<Employee> empList = query.list();
		
		assertNotNull(empList);
		assertEquals(0, empList.size());
	}
	
	@Test	//การ Query แบบ Criteria
	public void testCriteriaFindAllEmployee(){
		Criteria criteria = session.createCriteria(Employee.class);
		List<Employee> empList = criteria.list();
		
		assertNotNull(empList);
		assertEquals(0, empList.size());
	}
	
	@Test	// การ Query แบบ NamedQueries
	public void testNamedQueryFindAllEmployee(){
		Query query = session.getNamedQuery("FIND_ALL_EMPLOYEE");
		List<Employee> empList = query.list();
		
		assertNotNull(empList);
		assertEquals(0, empList.size());
	}
	
	@Test	// การ Query แบบ  Native Queries
	public void testNamedNativeQueryFindAllEmployee(){
		Query query = session.getNamedQuery("VIEW_EMPLOYEE");
		List<Employee> empList = query.list();
		
		assertNotNull(empList);
		assertEquals(0, empList.size());
	}
	
	@Test
	public void testFindByIdDepartment(){
		Employee employee = (Employee) session.get(Employee.class,1L);
		assertNotNull(employee);
		System.out.print(employee.getName());
	}
	
	@Test
	public void testDynamicQueryWithParametersByNameEmployee(){
		Query query = session.createQuery("FROM Employee WHERE name =:NAME");
		query.setParameter("NAME","Bank");
		List<Employee> empList = query.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testCriteriaWithParametersByNameEmployee(){
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.add(Restrictions.eq("name","Bank"));
		List<Employee> empList = criteria.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testNamedQueryWithParametersByNameEmployee(){
		Query query = session.getNamedQuery("FIND_BY_NAME_EMPLOYEE");
		query.setParameter("NAME", "Bank");
		List<Employee> empList = query.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testNamedNativeQueryWithParametersByNameEmployee(){
		Query query = session.getNamedQuery("VIEW_FIND_BY_NAME_EMPLOYEE");
		query.setParameter("NAME","Bank");
		List<Employee> empList = query.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testDynamicQueryWithParametersByNameAndEmailEmployee(){
		Query query = session.createQuery("FROM Employee WHERE name =:NAME AND email =:EMAIL");
		query.setParameter("NAME","Bank");
		query.setParameter("EMAIL","Bank@gmail.com");
		List<Employee> empList = query.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testCriteriaWithParametersByNameAndEmailEmployee(){
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.add(Restrictions.and(
				Restrictions.eq("name", "Bank"),
				Restrictions.eq("email", "Bank@gmail.com")
		));
		List<Employee> empList = criteria.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testNamedQueryWithParametersByNameAndEmailEmployee(){
		Query query = session.getNamedQuery("FIND_BY_NAME_AND_EMAIL_EMPLOYEE");
		query.setParameter("NAME","Bank");
		query.setParameter("EMAIL","Bank@gmail.com");
		List<Employee> empList = query.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testNamedNativeQueryWithParametersByNameAndEmailEmployee(){
		Query query = session.getNamedQuery("VIEW_FIND_BY_NAME_AND_EMAIL_EMPLOYEE");
		query.setParameter("NAME","Bank");
		query.setParameter("EMAIL","Bank@gmail.com");
		List<Employee> empList = query.list();
		assertEquals(empList.size(), 1);
	}
	
	public static SessionFactory createSessionFactory() {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
	            configuration.getProperties()). buildServiceRegistry();
	    return configuration.buildSessionFactory(serviceRegistry);
	}
}
