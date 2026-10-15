
package com.example.hibernate_cache_lab;

import com.example.service.DataInitService;
import com.example.service.PerformanceTestService;

public class App {

    public static void main(String[] args) {

        DataInitService dataInitService = new DataInitService();
        PerformanceTestService performanceTestService =
                new PerformanceTestService();

        try {
            // Initialiser les données
            System.out.println("=== Initialisation des données ===");
            dataInitService.initData();

            // Tester le chargement classique
            System.out.println("\n=== Test sans optimisation JOIN FETCH ===");
            performanceTestService.testPerformanceSansCache();

            // Tester JOIN FETCH
            System.out.println("\n=== Test avec JOIN FETCH ===");
            performanceTestService.testPerformanceAvecJoinFetch();

            // Tester les Entity Graphs
            System.out.println("\n=== Test avec Entity Graph ===");
            performanceTestService.testPerformanceAvecEntityGraph();


        } finally {
            performanceTestService.fermer();
            dataInitService.fermer();
        }
    }
}
