package services;

import domain.Parents;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;

public class ParentsService {
    public void saveParents(Parents parents) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getOpenSession()) {
            transaction = session.beginTransaction();
            session.save(parents);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateParents(Parents parents) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getOpenSession()) {
            transaction = session.beginTransaction();
            session.update(parents);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Parents> getParents() {
        try (Session session = HibernateUtil.getOpenSession()) {
            return session.createQuery("from Parents", Parents.class).list();
        }
    }

    public Parents getById(int id) {
        return this.getParents().stream().filter(x -> x.id == id).findFirst().get();
    }
}
