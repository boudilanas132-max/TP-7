
package com.example.hibernate_cache_lab;

import com.example.service.DataInitService;

public class DataInitTest {

    public static void main(String[] args) {
        DataInitService service = new DataInitService();

        try {
            service.initData();
        } finally {
            service.fermer();
        }
    }
}
