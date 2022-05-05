package com.revature.revagenda.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

//    @Column
//    @OneToMany
//    private List<Task> tasks;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName) {
        //this.tasks = new LinkedList<>();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public void addTask(Task task) {
//        this.tasks.add(task);
//    }
//
//    public void removeTask(Task task) {
//        this.tasks.remove(task);
//    }
//
//    public Task getTaskByName(String name) {
//        for (Task t : tasks) {
//            if(t.getName().equals(name)) {
//                return t;
//            }
//        }
//        return null;
//    }
}
