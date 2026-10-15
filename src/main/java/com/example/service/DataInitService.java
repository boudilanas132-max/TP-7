package com.example.service;

import com.example.model.Auteur;
import com.example.model.Livre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataInitService {

    private final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("hibernate-cache-pu");

    public void initData() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            for (int i = 1; i <= 10; i++) {
                Auteur auteur = new Auteur("Auteur " + i);

                for (int j = 1; j <= 5; j++) {
                    Livre livre = new Livre(
                        "Livre " + j + " de l'auteur " + i
                    );

                    auteur.ajouterLivre(livre);
                }

                em.persist(auteur);
            }

            em.getTransaction().commit();

            System.out.println("Initialisation terminée !");
            System.out.println("10 auteurs et 50 livres créés.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void fermer() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
