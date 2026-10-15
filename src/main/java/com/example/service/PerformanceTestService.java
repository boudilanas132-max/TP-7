package com.example.service;

import com.example.model.Auteur;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class PerformanceTestService {

    private final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("hibernate-cache-pu");

    public void testPerformanceSansCache() {
        EntityManager em = emf.createEntityManager();

        try {
            long start = System.currentTimeMillis();

            TypedQuery<Auteur> query =
                em.createQuery("SELECT a FROM Auteur a", Auteur.class);

            List<Auteur> auteurs = query.getResultList();

            // Charger les livres de chaque auteur
            for (Auteur auteur : auteurs) {
                auteur.getLivres().size();
            }

            long end = System.currentTimeMillis();

            System.out.println(
                "Nombre d'auteurs : " + auteurs.size()
            );

            System.out.println(
                "Durée de récupération : " + (end - start) + " ms"
            );

        } finally {
            em.close();
        }
    }

    public void fermer() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

public void testPerformanceAvecJoinFetch() {
    EntityManager em = emf.createEntityManager();

    try {
        long start = System.currentTimeMillis();

        TypedQuery<Auteur> query = em.createQuery(
            "SELECT DISTINCT a FROM Auteur a JOIN FETCH a.livres",
            Auteur.class
        );

        List<Auteur> auteurs = query.getResultList();

        // Les livres sont déjà chargés par JOIN FETCH
        for (Auteur auteur : auteurs) {
            auteur.getLivres().size();
        }

        long end = System.currentTimeMillis();

        System.out.println(
            "Nombre d'auteurs : " + auteurs.size()
        );

        System.out.println(
            "Durée avec JOIN FETCH : " + (end - start) + " ms"
        );

    } finally {
        em.close();
    }
    
}
public void testPerformanceAvecEntityGraph() {
    EntityManager em = emf.createEntityManager();

    try {
        long start = System.currentTimeMillis();

        EntityGraph<?> graph =
            em.getEntityGraph("Auteur.livres");

        TypedQuery<Auteur> query = em.createQuery(
            "SELECT a FROM Auteur a",
            Auteur.class
        );

        query.setHint("javax.persistence.fetchgraph", graph);

        List<Auteur> auteurs = query.getResultList();

        for (Auteur auteur : auteurs) {
            auteur.getLivres().size();
        }

        long end = System.currentTimeMillis();

        System.out.println("Nombre d'auteurs : " + auteurs.size());
        System.out.println(
            "Durée avec Entity Graph : " + (end - start) + " ms"
        );

    } finally {
        em.close();
    }
}
}
