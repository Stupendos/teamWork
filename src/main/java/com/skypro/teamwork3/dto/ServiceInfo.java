package com.skypro.teamwork3.dto;

public class ServiceInfo {

    private final String name;
    private final String version;

    public ServiceInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
