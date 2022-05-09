package com.revature.revagenda.beans.repositories;

import org.hibernate.Session;
import org.springframework.context.Lifecycle;

import java.io.Serializable;
import java.util.List;

public interface HibernateRepository<T> extends Lifecycle {
    public void save(T t);
    public List<T> getAll();
    public T getById(Integer id);


}
