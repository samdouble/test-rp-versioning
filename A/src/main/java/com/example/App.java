package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        String version = getVersion();
        System.out.println("Hello from A");
        System.out.println("Module A version: " + version);
    }

    private static String getVersion() {
        try (InputStream in = App.class.getResourceAsStream("/version.properties")) {
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                return p.getProperty("version", "unknown");
            }
        } catch (IOException e) {
            // ignore
        }
        return "unknown";
    }
}
