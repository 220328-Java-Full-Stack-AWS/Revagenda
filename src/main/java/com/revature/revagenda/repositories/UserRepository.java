package com.revature.revagenda.repositories;

import com.revature.revagenda.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.LinkedList;
import java.util.List;

public class UserRepository implements HibernateRepository<User>{
    private Session session;
    String tableName;

    public UserRepository(Session session) {
        this.session = session;
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
        return null;
    }

    public User getByUsername(String username) {
        return null;
    }
}
