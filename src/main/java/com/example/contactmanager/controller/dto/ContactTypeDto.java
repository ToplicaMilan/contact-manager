package com.example.contactmanager.controller.dto;

import javax.validation.constraints.NotEmpty;

public class ContactTypeDto {

    @NotEmpty(message = "must not be empty")
    private String description;

    @NotEmpty(message = "must not be empty")
    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
