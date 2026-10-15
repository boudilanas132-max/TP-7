package com.example.hibernate_cache_lab;

import com.example.service.PerformanceTestService;

public class PerformanceTest {


public static void main(String[] args) {
    PerformanceTestService service = new PerformanceTestService();

    try {
        System.out.println("=== Test avec chargement LAZY ===");
        service.testPerformanceSansCache();

        System.out.println("\n=== Test avec JOIN FETCH ===");
        service.testPerformanceAvecJoinFetch();
        
        System.out.println("\n=== Test avec Entity Graph ===");
        service.testPerformanceAvecEntityGraph();

    } finally {
        service.fermer();
    }
}


}