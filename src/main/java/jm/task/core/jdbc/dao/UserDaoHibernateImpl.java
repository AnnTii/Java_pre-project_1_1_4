package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE User(id INT AUTO_INCREMENT PRIMARY KEY,name CHAR(45),lastName CHAR(45), age INT)").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS `preproject`.`User`").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            for (User user : users) {
                System.out.println(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
