package com.hibernate.annotation.db.model;


import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DepartmentTest {

	private static final SessionFactory sessionFactory = createSessionFactory();
	
	Session session;
	
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
		Department department = new Department();
		department.setName("Programmer");
		department.setPhoneNumber("084-111-1111");
		session.save(department);
		session.getTransaction().commit();		
	}
	
	@Test
	public void testUpdateEntity(){
		session.getTransaction().begin();
		Department department = (Department)session.get(Department.class,1);
		department.setName("Programmer");
		session.update(department);
		session.getTransaction().commit();
	}
	
	@Test
	public void testDeleteEntity(){
		session.getTransaction().begin();
		Department department = (Department)session.get(Department.class, 1);
		session.delete(department);
		session.getTransaction().commit();
	}
	
	@Test	//การ Query แบบ  Dynamic Queries
	public void testDynamicQueryFindAllDepartment(){
		Query query = session.createQuery("FROM Department");
		List<Department> departments = query.list();
		
		assertNotNull(departments);
		assertEquals(0,departments.size());
	}
	
	@Test	//การ Query แบบ Criteria
	public void testCriteriaFindAllDepartment(){
		Criteria criteria = session.createCriteria(Department.class);
		List<Department> department = criteria.list();
		
		assertNotNull(department);
		assertEquals(0,department.size());
	}
	
	@Test	// การ Query แบบ NamedQueries
	public void testNamedQueryFindAllDepartment(){
		Query query = session.getNamedQuery("FIND_ALL_DEPARTMENT");
		List<Department> department = query.list();
		
		assertNotNull(department);
		assertEquals(0,department.size());
	}
	
	@Test	// การ Query แบบ  Native Queries
	public void testNamedNativeQueryFindAllDepartment(){
		Query query = session.getNamedQuery("VIEW_DEPARTMENT");
		List<Department> departments = query.list();
		
		assertNotNull(departments);
		assertEquals(0, departments.size());
	}
	
	@Test
	public void testFindByIdDepartment(){
		Department departments = (Department) session.get(Department.class,1L);
		assertNotNull(departments);
		System.out.print(departments.getName());
	}
	
	@Test
	public void testFindByPhoneNumberWithDepartment(){
		Query query = session.createQuery("FROM Department d WHERE PHONE_NUMBER LIKE(:Department_phoneNumber)");
		query.setParameter("Department_phoneNumber","084%");
		List<Department> departments = query.list();
		assertEquals(departments.size(), 3);
	}
	
	@Test
	public void testFindByPhoneNumberCriteriaDepartment(){
		Criteria criteria = session.createCriteria(Department.class);
		criteria.add(Restrictions.like("phoneNumber","084%"));
		List<Department> departments = criteria.list();	
		assertNotNull(departments);
	}
	
	@Test
	public void testNamedQueryWithParametersByPhoneNumberDepartment(){
		Query query = session.getNamedQuery("FIND_BY_PHONE_NUMBER_DEPARTMENT");
		query.setParameter("PHONE_NUMBER", "084%");
		List<Employee> empList = query.list();
		assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testNamedNativeQueryWithParametersByPhoneNumberDepartment(){
		Query query = session.getNamedQuery("VIEW_FIND_BY_PHONE_NUMBER_DEPARTMENT");
		query.setParameter("PHONE_NUMBER","084%");
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

