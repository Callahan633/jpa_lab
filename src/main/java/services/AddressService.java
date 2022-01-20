package services;

import domain.Address;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;


public class AddressService {
    public void saveAddress(Address address) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getOpenSession()) {
            transaction = session.beginTransaction();
            session.save(address);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Address> getAddress() {
        try (Session session = HibernateUtil.getOpenSession()) {
            return session.createQuery("from Address", Address.class).list();
        }
    }

    public Address getById(int id) {
        return this.getAddress().stream().filter(x -> x.id == id).findFirst().get();
    }
}
