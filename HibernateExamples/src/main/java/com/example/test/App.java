package com.example.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.example.entities.Address;
import com.example.entities.Employee;
import com.example.entities.Payment;
import com.example.entities.Project;
import com.example.entities.Vehicle;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Employee emp = new Employee();
		Employee emp2 = new Employee();
		emp2.setAge(23);
		emp2.setName("piyush");
		emp2.setCreatedOn(new Date());
		emp.setAge(5);
		emp.setName("aman bal");
		emp.setCreatedOn(new Date());
		Address address = new Address();
		address.setCity("kanpur");
		address.setCountry("india");
		address.setState("UP");
		emp.setHomeAddress(address);
		emp.setOfficeAddress(address);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleType("CAR");
		vehicle.setVehicleName("MARUTI SUZUKI");
		emp.setVehicle(vehicle);
		Payment payment1 = new Payment();
		Payment payment2 = new Payment();
		payment1.setPaymentName("PAYTM");
		payment1.setPaymentType("UPI");
		payment2.setPaymentName("PAYTM");
		payment2.setPaymentType("UPI");
		emp.getPayments().add(payment1);
		emp.getPayments().add(payment2);
		payment1.setEmployee(emp);
		payment2.setEmployee(emp);
		
		Project project1=new Project();
		Project project2=new Project();
		project1.setProjectName("vestige");
		project1.getEmployees().add(emp);
		project1.getEmployees().add(emp2);
		project1.setProjectName("zoylo");
		project2.getEmployees().add(emp2);
		emp.getProjects().add(project1);
		emp2.getProjects().add(project2);
		emp.getProjects().add(project1);
		session.save(project2);
		session.save(project1);
		session.save(payment1);
		session.save(payment2);
		session.save(emp);
		session.save(emp2);

		
		Query query=session.createQuery("from Employee where id =?");
		query.setLong(0, 1l);
		List<Employee> employee= (List<Employee>) query.list();
		System.out.println(employee);
		
		Query query2=session.createQuery("from Employee where name =?");
		query2.setString(0, "aman bal");
		query2.setCacheable(true); // check in second level cache and cache the result
		List<Employee> employee2= (List<Employee>) query2.list();
		System.out.println(employee2);

		Query query3=session.getNamedQuery("Employee.byId");
		query3.setInteger(0, 1);
		List<Employee> employee3= (List<Employee>) query3.list();
		System.out.println(employee3);
		
		Query query4=session.getNamedQuery("Employee.byName");
		query4.setString(0, "aman bal");
		List<Employee> employee4= (List<Employee>) query4.list();
		System.out.println(employee4);
		
		Criteria criteria=session.createCriteria(Employee.class);
		criteria.add(Restrictions.idEq(2l));
		List<Employee> employee5= (List<Employee>) criteria.list();
		System.out.println(employee5);
		
		Criteria criteria2=session.createCriteria(Employee.class);
		criteria2.setProjection(Projections.count("id")).addOrder(Order.asc("id"));
		List<Employee> employee6= (List<Employee>) criteria2.list();
		System.out.println(employee6);
		
		Employee example=new Employee();
		example.setAge(22);
		example.setName("krish");
		
		Example ex=Example.create(example);
		ex.excludeProperty("name");
		Criteria criteria3=session.createCriteria(Employee.class);
		criteria3.add(ex);
		List<Employee> employee7= (List<Employee>) criteria3.list();
		System.out.println(employee7);
		
		Query query6=session.createQuery("from Employee where name =?");
		query6.setString(0, "aman bal");
		query6.setCacheable(true);// check in second level cache and cache the result
		List<Employee> employee8= (List<Employee>) query2.list();
		System.out.println(employee2);
		
		tx.commit();
		// retrieve data
//		session = sessionFactory.openSession();
//		emp = (Employee) session.get(Employee.class, 1l); // entity class and primary key
//		System.out.println(emp.getPayments());

	}
}
