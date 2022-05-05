package com.revature.revagenda.repositories;

import com.revature.revagenda.entities.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TaskRepository implements HibernateRepository<Task>{
    private Session session;

    public TaskRepository(Session session) {
        this.session = session;
    }

    @Override
    public void save(Task task) {
        Transaction tx = session.beginTransaction();
        session.save(task);
        tx.commit();
    }

    @Override
    public List<Task> getAll() {
        return null;
    }

    @Override
    public Task getById(Integer id) {
        return null;
    }
}
