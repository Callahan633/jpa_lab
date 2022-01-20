package services;

import domain.Education;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;

public class EducationService {
    public void saveEducation(Education education) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getOpenSession()) {
            transaction = session.beginTransaction();
            session.save(education);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Education> getEducation() {
        try (Session session = HibernateUtil.getOpenSession()) {
            return session.createQuery("from Education", Education.class).list();
        }
    }

    public Education getById(int id) {
        return this.getEducation().stream().filter(x -> x.id == id).findFirst().get();
    }
}
