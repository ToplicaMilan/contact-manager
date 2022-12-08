package com.example.contactmanager.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ContactTypeDto {

    @NotEmpty(message = "Must not be empty", groups = OnCreate.class)
    @NotNull(message = "Must not be null")
    private String description;

    @NotEmpty(message = "Must not be empty", groups = {OnUpdate.class, OnCreate.class})
    @NotNull(message = "Must not be null")
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

    public interface OnCreate {
    }

    public interface OnUpdate {
    }
}
