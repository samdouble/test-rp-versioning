package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class VersionInfo {
    private VersionInfo() {}


    public static String getVersion(Class<?> resourceContext) {
        try (InputStream in = resourceContext.getResourceAsStream("/version.properties")) {
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
