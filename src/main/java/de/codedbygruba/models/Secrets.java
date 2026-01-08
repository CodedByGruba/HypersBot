package de.codedbygruba.models;

import lombok.Getter;

import java.util.List;

@Getter
public class Secrets {
    private String token;
    private String googleSheetUrl;
    private String googleSheetBackupUrl;

    private List<String> allowedRoles;
}
