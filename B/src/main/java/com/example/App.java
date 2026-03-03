package com.example;

public class App {
    public static void main(String[] args) {
        String version = VersionInfo.getVersion(App.class);
        System.out.println("Hello from B");
        System.out.println("Module B version: " + version);
    }
}
