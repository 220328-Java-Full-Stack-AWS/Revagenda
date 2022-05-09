package com.revature.revagenda.beans.repositories;

import com.revature.revagenda.beans.services.StorageManager;
import com.revature.revagenda.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@Component
public class UserRepository implements HibernateRepository<User>, Lifecycle {
    private final StorageManager storageManager;
    private boolean running = false;
    private Session session;
    String tableName;

    @Autowired
    public UserRepository(StorageManager storageManager) {
        this.storageManager = storageManager;
        this.tableName = "users";
    }

    @Override
    public void save(User user) {
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        Query query = session.createNativeQuery(sql);
        //query.setParameter("table", tableName);
        List<Object[]> results = query.getResultList();

        List<User> userList = new LinkedList<>();
        for(Object[] result : results) {
            User user = new User();
            user.setId((Integer)result[0]);
            user.setFirstName((String)result[1]);
            user.setLastName((String)result[2]);
            user.setPassword((String)result[3]);
            user.setUsername((String)result[4]);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public User getById(Integer id) {
        String hql = "FROM User WHERE id = :id";
        TypedQuery<User> query = session.createQuery(hql, User.class);

        query.setParameter("id", id);

        User user = query.getSingleResult();

        return user;
    }

    public User getByUsername(String username) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

        Root<User> userTable = query.from(User.class);
        query.select(userTable)
                .where(criteriaBuilder.equal(userTable.get("username"), username));

        return session.createQuery(query).getSingleResult();
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
        return running;
    }
}
