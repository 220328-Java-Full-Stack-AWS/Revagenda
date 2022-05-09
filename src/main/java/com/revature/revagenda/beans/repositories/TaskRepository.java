package com.revature.revagenda.beans.repositories;

import com.revature.revagenda.beans.services.StorageManager;
import com.revature.revagenda.entities.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository //These are repository beans, but this is just a marker. This doesn't actually change the behavior of this class.
public class TaskRepository implements HibernateRepository<Task> {
    private final StorageManager storageManager;
    private Session session;
    private boolean running = false;


    @Autowired
    public TaskRepository(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    public void save(Task task) {
        Transaction tx = session.beginTransaction();
        session.save(task);
        tx.commit();
    }

    @Override
    public List<Task> getAll() {
        String hql = "FROM Task";
        TypedQuery<Task> query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Task getById(Integer id) {
        String hql = "FROM Task WHERE id = :id";
        TypedQuery<Task> query = session.createQuery(hql);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void start() {
        this.session = storageManager.getSession();
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
