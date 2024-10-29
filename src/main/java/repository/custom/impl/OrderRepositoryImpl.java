package repository.custom.impl;

import entity.OrderEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public List<OrderEntity> findAll() {
        Transaction transaction = null;
        List<OrderEntity> orderEntityList = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            orderEntityList = session.createQuery("from OrderEntity", OrderEntity.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return orderEntityList;
    }
}
