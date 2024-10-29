package repository.custom.impl;

import entity.CustomerEntity;
import entity.OrderEntity;
import entity.OrderItemsEntity;
import entity.ProductEntity;
import model.Customer;
import model.OrderItems;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.OrderItemsRepository;

import java.util.List;

public class OrderItemsRepositoryImpl implements OrderItemsRepository {
    @Override
    public List<OrderItemsEntity> findAll() {
        Transaction transaction = null;
        List<OrderItemsEntity> orderItemsEntityList = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            orderItemsEntityList = session.createQuery("from OrderItemsEntity", OrderItemsEntity.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return orderItemsEntityList;
    }

    @Override
    public List<OrderItemsEntity> findByOrderID(String orderId) {
        List<OrderItemsEntity> orderItems = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "SELECT oi FROM OrderItemsEntity oi " +
                    "JOIN oi.orderEntity o " +
                    "WHERE o.orderId = :orderId";

            Query<OrderItemsEntity> query = session.createQuery(hql, OrderItemsEntity.class);
            query.setParameter("orderId", orderId);
            orderItems = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public void save(OrderItemsEntity orderItemsEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Query<CustomerEntity> customerEntityQuery = session.createQuery("FROM CustomerEntity WHERE name = :name", CustomerEntity.class);
            customerEntityQuery.setParameter("name", orderItemsEntity.getOrderEntity().getCustomerEntity().getName());
            CustomerEntity customerEntity = customerEntityQuery.uniqueResult();

            if (customerEntity == null) {
                customerEntity = new CustomerEntity();
                customerEntity.setName(orderItemsEntity.getOrderEntity().getCustomerEntity().getName());
                customerEntity.setEmail(orderItemsEntity.getOrderEntity().getCustomerEntity().getEmail());
                customerEntity.setPhoneNumber(orderItemsEntity.getOrderEntity().getCustomerEntity().getPhoneNumber());
                session.save(customerEntity);
                session.flush();
            }

            Query<OrderEntity> orderEntityQuery = session.createQuery("FROM OrderEntity WHERE customerEntity = :customer AND date = :date AND time = :time", OrderEntity.class);
            orderEntityQuery.setParameter("customer", customerEntity);
            orderEntityQuery.setParameter("date", orderItemsEntity.getOrderEntity().getDate());
            orderEntityQuery.setParameter("time", orderItemsEntity.getOrderEntity().getTime());
            OrderEntity orderEntity = orderEntityQuery.uniqueResult();

            if (orderEntity == null) {
                orderEntity = new OrderEntity();
                orderEntity.setCustomerEntity(customerEntity);
                orderEntity.setDate(orderItemsEntity.getOrderEntity().getDate());
                orderEntity.setTime(orderItemsEntity.getOrderEntity().getTime());
                orderEntity.setTotal(orderItemsEntity.getOrderEntity().getTotal());
                orderEntity.setPaymentType(orderItemsEntity.getOrderEntity().getPaymentType());
                orderEntity.setOrderItemCount(orderItemsEntity.getOrderEntity().getOrderItemCount());
                session.save(orderEntity);
                session.flush();
            }

            orderItemsEntity.setOrderEntity(orderEntity);

            session.save(orderItemsEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
