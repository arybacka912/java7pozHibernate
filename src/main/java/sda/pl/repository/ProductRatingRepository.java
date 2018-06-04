package sda.pl.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import sda.pl.HibernateUtil;
import sda.pl.domain.ProductRating;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductRatingRepository {
    public static Long saveOrUpdate(ProductRating productRating) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(productRating);
            session.getTransaction().commit();
            return null;
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return -1L;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public static Optional<ProductRating> findProductRating(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            return Optional.ofNullable(session.find(ProductRating.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    public static List<ProductRating> findAllActiveByProductId(Long productId) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "select pr from ProductRating pr where (pr.product.id = :id) " +
                    "and (pr.isActive = true or pr.rate > 4) order by pr.id desc ";
            Query query = session.createQuery(hql);
            query.setParameter("id", productId);
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