package com.example.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.example.entities.Employee;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SessionFactory sessionFactory = new AnnotationConfiguration()
				.configure().buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();

		Employee emp = new Employee();
		emp.setAge(5);
		emp.setName("ajeet bal");
		
		session.save(emp);

		tx.commit();
	}
}
