package com.revature.revagenda;

import com.revature.revagenda.entities.Task;
import com.revature.revagenda.entities.User;
import com.revature.revagenda.utilities.StorageManager;
import com.revature.revagenda.utilities.TransactionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RevagendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevagendaApplication.class, args);

		StorageManager storageManager = new StorageManager();
		storageManager.addAnnotatedClass(Task.class);
		storageManager.addAnnotatedClass(User.class);

		/*
		Here we are initializing our Hibernate session, and at this point because our hbm2ddl setting is set
		to true, all tables are dropped and re-created here.
		 */
		Session session = storageManager.initializeDatasource();


		/*
		Prepare the transaction manager object and "dependency inject" the session object we got earlier
		 */
		TransactionManager transactionManager = new TransactionManager(session);


		/*
		Here we begin doing DML and inserting (saving) stuff into the database. We begin the transaction, make the
		changes, and commit it.
		 */
		Transaction tx = transactionManager.beginTransaction();
		Task oilChange = new Task("oilchange", "Get your oil changed.");
		Task carInspection = new Task("inspection", "Get your car inspected.");
		User newUser = new User("kplummer", "password", "Kyle", "Plummer");
		oilChange.setUser(newUser);
		carInspection.setUser(newUser);
		//newUser.addTask(oilChange);
		//newUser.addTask(carInspection);
		session.save(newUser);
		session.save(oilChange);
		session.save(carInspection);
		Task newTask = new Task("Refactor Revagenda", "re-write revagenda to add in hibernate.");
		session.save(newTask);
		transactionManager.commitTransaction(tx);//unnecessary over-engineering?

		tx = transactionManager.beginTransaction();
		//newUser.addTask(newTask);
		newTask.setUser(newUser);
//		session.save(newUser);
		transactionManager.commitTransaction(tx);
	}

}
