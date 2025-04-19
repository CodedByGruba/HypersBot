package de.codedbygruba.utils;

import java.util.List;

public class PermissionManager {
    private static PermissionManager INSTANCE;
    public static PermissionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PermissionManager();
        }
        return INSTANCE;
    }

    public List<String> allowedRoles = List.of(
            "1229182145235255421", "1229182187182751797",
            "1309887484133638266", "1231949907892633632",
            "1234966150756962385", "1355676921391747113"
    );
}
