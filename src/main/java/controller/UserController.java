package controller;

import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException; // Import the NoResultException
import java.util.Scanner;

public class UserController {
    public boolean Register() {
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String password = scanner.nextLine();

        User user = new User(username, password);
        EntityTransaction transaction = null;

        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
            SessionController.SetUser(user);
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
           // e.printStackTrace(); // You might want to log this to a file instead
        } finally {
            entityManager.close();
        }
        return false;
    }

    public boolean Login() {
        Scanner scanner = new Scanner(System.in);
        String typedUsername = scanner.nextLine();
        String typedPassword = scanner.nextLine();

        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            User user = entityManager.createQuery("FROM User WHERE username = :username AND password = :password", User.class)
                    .setParameter("username", typedUsername)
                    .setParameter("password", typedPassword)
                    .getSingleResult();

            if (user != null) {
                SessionController.SetUser(user);
                return true;
            }
        } catch (NoResultException e) {
            // Do nothing, suppress the error message
        } catch (Exception e) {
           // e.printStackTrace(); // Consider logging this instead
        } finally {
            entityManager.close();
        }
        return false;
    }
}
