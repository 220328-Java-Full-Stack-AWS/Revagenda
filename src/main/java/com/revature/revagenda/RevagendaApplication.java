package com.revature.revagenda;

import com.revature.revagenda.entities.Task;
import com.revature.revagenda.entities.User;
import com.revature.revagenda.repositories.TaskRepository;
import com.revature.revagenda.repositories.UserRepository;
import com.revature.revagenda.utilities.StorageManager;
import com.revature.revagenda.utilities.TransactionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

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
		User kyle = new User("kplummer", "password", "Kyle", "Plummer");
		User giorgi = new User("gamirajibi", "password", "Giorgi", "Amirajibi");
		User kenneth = new User("kstrohm", "password", "Kenneth", "Strohm");


		kyle.addTask(new Task(kyle, "oilchange", "Get your oil changed."));
		kyle.addTask(new Task(kyle, "inspection", "Get your car inspected."));

		session.save(kyle);
		session.save(giorgi);
		session.save(kenneth);


		transactionManager.commitTransaction(tx);//unnecessary over-engineering?

		Task newTask = new Task(kyle,"Refactor Revagenda", "re-write revagenda to add in hibernate.");




		tx = transactionManager.beginTransaction();
		kyle.addTask(newTask);

		UserRepository userRepository = new UserRepository(session);


		/*
		This method uses native SQL query to get all users from the users table.
		 */
		List<User> users = userRepository.getAll();

		transactionManager.commitTransaction(tx);

		for (User u : users) {
			System.out.println("Username: " + u.getUsername());
		}

		/*
		This method uses HQL to query by unique ID
		 */
		User user1 = userRepository.getById(1);
		System.out.println("User 1: " + user1.getUsername());


		/*
		This method uses the criteriabuilder to query for a unique user object based on unique username
		 */
		User userKstrohm = userRepository.getByUsername("kstrohm");
		System.out.println("User kstrohm: " + userKstrohm.getUsername() + ", " + userKstrohm.getId());


		TaskRepository taskRepository = new TaskRepository(session);
		Task task = taskRepository.getById(1);
		System.out.println("Task: " + task.getName() + ", " + task.getDescription());

		List<Task> taskList = taskRepository.getAll();
		for (Task t : taskList) {
			System.out.println("Task: " + t.getDescription());
		}

		Task task2 = taskRepository.getById(2);
		System.out.println("Task 2: " + task2.getDescription());


	}

}
