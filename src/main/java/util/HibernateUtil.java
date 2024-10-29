package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    private HibernateUtil(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static HibernateUtil getInstance(){
        return hibernateUtil==null? new HibernateUtil():hibernateUtil;
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
