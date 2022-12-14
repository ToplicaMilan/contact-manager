package com.example.contactmanager.controller.dto;

import javax.validation.constraints.NotBlank;

public class ContactTypeDto {

    @NotBlank(message = "Must not be blank", groups = OnCreate.class)
    private String description;

    @NotBlank(message = "Must not be blank", groups = {OnUpdate.class, OnCreate.class})
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
