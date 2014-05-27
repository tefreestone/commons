package edu.byu.core.common.wsAuth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

public abstract class BaseAbstractDAO {


    @Resource(name = "wsAuthSessionFactory")
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
