package repository.custom.impl;

import entity.CategoryEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.CategoryRepository;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public List<CategoryEntity> findAll() {
        Transaction transaction = null;
        List<CategoryEntity> categoryEntityList = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            categoryEntityList = session.createQuery("from CategoryEntity", CategoryEntity.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return categoryEntityList;
    }

    @Override
    public CategoryEntity findByName(String categoryName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        CategoryEntity categoryEntity = null;

        try {
            transaction = session.beginTransaction();
            String hql = "FROM CategoryEntity WHERE name = :categoryName";
            Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
            query.setParameter("categoryName", categoryName);
            categoryEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return categoryEntity;
    }

    @Override
    public CategoryEntity findByCategoryID(String categoryID) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        CategoryEntity categoryEntity = null;

        try {
            transaction = session.beginTransaction();
            String hql = "FROM CategoryEntity WHERE categoryId = :categoryId";
            Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
            query.setParameter("categoryId", categoryID);
            categoryEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return categoryEntity;
    }
}
