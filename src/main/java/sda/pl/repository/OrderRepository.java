package sda.pl.repository;

import org.hibernate.Session;
import sda.pl.HibernateUtil;
import sda.pl.Product;
import sda.pl.domain.Order;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    public static boolean saveOrder(Order orger) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.save(orger);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Order> findAll() {
        Session session = null;

        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT o FROM Order o JOIN FETCH o.orderDetailSet";
            Query query = session.createQuery(hql);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
    public static List<Order> findAllWithProductId(String productName) {
        Session session = null;

        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT o FROM Order o JOIN FETCH o.orderDetailSet od WHERE UPPER(od.product.name) like :productName";
            Query query = session.createQuery(hql);
            query.setParameter("productName", "%" + productName.toUpperCase() + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
