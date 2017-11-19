package com.ml.spring.tuto.authal.enums;

public enum RolesEnum {

    USER(1, "ROLE_USER", "User"),
    ADMIN(2, "ROLE_ADMIN", "Admin");

    private int id;

    private String name;

    private String description;

    private int priority;

    RolesEnum(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
