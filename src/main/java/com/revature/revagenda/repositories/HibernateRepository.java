package com.revature.revagenda.repositories;

import org.hibernate.Session;

import java.util.List;

public interface HibernateRepository<T> {
    public void save(T t);
    public List<T> getAll();
    public T getById(Integer id);


}
