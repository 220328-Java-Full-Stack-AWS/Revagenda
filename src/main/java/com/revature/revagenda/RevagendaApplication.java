package com.revature.revagenda;

import com.revature.revagenda.entities.Task;
import com.revature.revagenda.entities.User;
import com.revature.revagenda.beans.repositories.TaskRepository;
import com.revature.revagenda.beans.repositories.UserRepository;
import com.revature.revagenda.beans.services.StorageManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication(scanBasePackages = "com.revature.revagenda.beans")
public class RevagendaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RevagendaApplication.class, args);

		/*
		Here we are initializing our Hibernate session, and at this point because our hbm2ddl setting is set
		to true, all tables are dropped and re-created.
		 */
		StorageManager storageManager = context.getBean(StorageManager.class);
		storageManager.addEntity(Task.class);
		storageManager.addEntity(User.class);

		context.start(); //send the start signal to all lifecycle hooks - it gets to our storage manager and repository beans
		Session session = storageManager.getSession(); //once the start signal is sent and the lifecycle hooks are invoked, our sessions are valid

		//Get references to our repository beans. Keep in mind these aren't JPA repositories.
		UserRepository userRepository = context.getBean(UserRepository.class);
		TaskRepository taskRepository = context.getBean(TaskRepository.class);

		/*
		Here we begin doing DML and inserting (saving) stuff into the database. We begin the transaction, make the
		changes, and commit it.
		 */
		Transaction tx = session.beginTransaction();

		User kyle = new User("kplummer", "password", "Kyle", "Plummer");
		User giorgi = new User("gamirajibi", "password", "Giorgi", "Amirajibi");
		User kenneth = new User("kstrohm", "password", "Kenneth", "Strohm");

		kyle.addTask(new Task(kyle, "oilchange", "Get your oil changed."));
		kyle.addTask(new Task(kyle, "inspection", "Get your car inspected."));

		session.save(kyle);
		session.save(giorgi);
		session.save(kenneth);

		tx.commit();



		tx = session.beginTransaction();
		Task newTask = new Task(kyle,"Refactor Revagenda", "re-write revagenda to add in hibernate.");
		kyle.addTask(newTask);
		tx.commit();



		/*
		This method uses native SQL query to get all users from the users table.
		 */
		List<User> users = userRepository.getAll();
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
