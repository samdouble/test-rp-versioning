package com.example;

public class App {
    public static void main(String[] args) {
        String version = VersionInfo.getVersion(App.class);
        System.out.println("Hello from A");
        System.out.println("Module A version: " + version);
    }
}
