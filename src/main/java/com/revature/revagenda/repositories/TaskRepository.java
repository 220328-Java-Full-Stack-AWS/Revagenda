package com.revature.revagenda.repositories;

import com.revature.revagenda.entities.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
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
}
