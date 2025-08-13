package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static EntityManager entityManager;

    public static void init() {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPU");
            entityManager = entityManagerFactory.createEntityManager();
            System.out.println("Successfully connected to the database!");
        } catch (Exception e) {
            System.err.println("Error initializing database connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Initialize EntityManager
        init();

        if (entityManager == null) {
            System.err.println("Failed to initialize EntityManager. Exiting...");
            return;
        }

        try {
            // Create a new user
            User user = new User();
            user.setName("Anna");
            user.setEmail("anna@example.com");

            // Begin transaction
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();

            System.out.println("User saved successfully!");

            User user2 = entityManager.find(User.class, 1L);
            System.out.println(user2.getName());

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error saving user: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}