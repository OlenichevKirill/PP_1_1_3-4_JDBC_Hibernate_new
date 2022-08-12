package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User\n" +
                    "(\n" +
                    "    ID        BIGINT PRIMARY KEY NOT NULL auto_increment,\n" +
                    "    NAME      TEXT                 NOT NULL,\n" +
                    "    LAST_NAME TEXT                 NOT NULL,\n" +
                    "    AGE       TINYINT              NOT NULL\n" +
                    ");").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {

        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User;").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                session.getTransaction().commit();
                System.out.println("User with the name: " + name + " added to the database");
            } catch (HibernateException e) {
                session.getTransaction().rollback();
            }
        } catch (HibernateException e) {

        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                User user = session.get(User.class, id);
                session.remove(user);
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
            }
        } catch (HibernateException e) {

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            userList = session.createQuery("SELECT u FROM User u", User.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {

        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User u").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {

        }
    }
}
