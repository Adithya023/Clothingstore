package repository.custom.impl;

import entity.CategoryEntity;
import entity.ProductEntity;
import entity.SupplierEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.ProductRepository;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public boolean save(ProductEntity entity) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            if (entity.getCategoryEntity() != null) {
                CategoryEntity existingCategory = session.createQuery("FROM CategoryEntity WHERE name = :name", CategoryEntity.class)
                        .setParameter("name", entity.getCategoryEntity().getName())
                        .uniqueResult();

                if (existingCategory != null) {
                    entity.setCategoryEntity(existingCategory);
                } else {
                    session.save(entity.getCategoryEntity());
                    session.flush();
                }
            }
            if (entity.getSupplierEntity() != null) {
                SupplierEntity existingSupplier = session.createQuery("FROM SupplierEntity WHERE name = :name", SupplierEntity.class)
                        .setParameter("name", entity.getSupplierEntity().getName())
                        .uniqueResult();

                if (existingSupplier != null) {
                    entity.setSupplierEntity(existingSupplier);
                } else {
                    session.save(entity.getSupplierEntity());
                    session.flush();
                }
            }

            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(ProductEntity entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            String productHql = "FROM ProductEntity WHERE productId = :productId";
            Query<ProductEntity> productQuery = session.createQuery(productHql, ProductEntity.class);
            productQuery.setParameter("productId", entity.getProductId());

            ProductEntity existingProduct = productQuery.uniqueResult();

            if (existingProduct != null) {
                existingProduct.setName(entity.getName());
                existingProduct.setQuantity(entity.getQuantity());
                existingProduct.setUnitPrice(entity.getUnitPrice());

                if (entity.getCategoryEntity()!=null){
                    String categoryHql = "FROM CategoryEntity WHERE categoryId = :name";
                    Query<CategoryEntity> categoryQuery = session.createQuery(categoryHql, CategoryEntity.class);
                    categoryQuery.setParameter("name", entity.getCategoryEntity().getName());
                    CategoryEntity existingCategory = categoryQuery.uniqueResult();

                    if (existingCategory != null) {
                        existingProduct.setCategoryEntity(existingCategory);
                    }
                }

                if(entity.getSupplierEntity()!=null){
                    String supplierHql = "FROM SupplierEntity WHERE supplierId = :supplierId";
                    Query<SupplierEntity> supplierQuery = session.createQuery(supplierHql, SupplierEntity.class);
                    supplierQuery.setParameter("supplierId", entity.getSupplierEntity().getSupplierId());
                    SupplierEntity existingSupplier = supplierQuery.uniqueResult();

                    if (existingSupplier != null) {
                        existingProduct.setSupplierEntity(existingSupplier);
                    }
                }else{
                    existingProduct.setSupplierEntity(null);
                }

                session.update(existingProduct);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<ProductEntity> findAll() {
        Transaction transaction = null;
        List<ProductEntity> productEntityList = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            productEntityList = session.createQuery("from ProductEntity", ProductEntity.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return productEntityList;
    }

    @Override
    public ProductEntity findByID(String productId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        ProductEntity productEntity = null;
        try {
            transaction = session.beginTransaction();
            String hql = "FROM ProductEntity WHERE productId = :productId";
            Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
            query.setParameter("productId", productId);
            productEntity = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productEntity;
    }

    @Override
    public void deleteByID(String productId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM ProductEntity WHERE productId = :productId";
            Query<ProductEntity> query = session.createQuery(hql);
            query.setParameter("productId", productId);

            int result = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<ProductEntity> findBySupplierID(String supplierId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<ProductEntity> productEntityList = null;
        try {
            transaction = session.beginTransaction();
            String hql = "FROM ProductEntity p WHERE p.supplierEntity.supplierId = :supplierId";
            Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
            query.setParameter("supplierId", supplierId);
            productEntityList = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productEntityList;
    }

}
