package repository;

import org.hibernate.SessionFactory;
import util.HibernateUtil;

public interface SuperRepository {
    SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
}
