package repository.custom.impl;

import entity.CustomerEntity;
import entity.ProductEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.CustomerRepository;

import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public List<CustomerEntity> findAll() {
        Transaction transaction = null;
        List<CustomerEntity> customerEntityList = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            customerEntityList = session.createQuery("from CustomerEntity", CustomerEntity.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return customerEntityList;
    }
}
