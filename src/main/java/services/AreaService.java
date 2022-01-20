package services;

import domain.Area;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;

public class AreaService {
    public void saveArea(Area area) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getOpenSession()) {
            transaction = session.beginTransaction();
            session.save(area);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Area> getAreas() {
        try (Session session = HibernateUtil.getOpenSession()) {
            return session.createQuery("from Area", Area.class).list();
        }
    }
}
