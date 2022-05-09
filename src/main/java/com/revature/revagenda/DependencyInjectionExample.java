package com.revature.revagenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component//marked as component just for the autowired constructor example below
public class DependencyInjectionExample {
    /*
    This class depends on some other object, let's just use a bare Java Object
     */
    private final Object dependentObject;

    //No dependency injection, very tightly coupled
    public DependencyInjectionExample() {
        this.dependentObject = new Object();
    }

    //basic dependency injection, slightly looser coupling
    public DependencyInjectionExample(Object obj) {
        this.dependentObject = obj;
    }
    //But we would still have to hand the object off when we invoke this constructor. We can be even lazier with spring beans:

    //Spring IoC Dependency Injection - extremely loose coupling
    @Autowired
    public DependencyInjectionExample(Bean bean) {
        this.dependentObject = bean;
    }



}
