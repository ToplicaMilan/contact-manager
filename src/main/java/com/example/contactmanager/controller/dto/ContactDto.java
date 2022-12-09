package com.example.contactmanager.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ContactDto {

    @NotEmpty(message = "Must not be empty", groups = OnCreate.class)
    @NotBlank
    @NotNull
    private String firstName;

//    @NotEmpty(message = "Must not be empty")
    @NotNull
    @NotBlank
    private String lastName;

//    @NotEmpty(message = "Must not be empty")
    @NotNull
    @NotBlank
    private String address;

    @NotEmpty(message = "Must not be empty")
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$", message = "Not valid number", groups = OnCreate.class)
    private String phoneNumber;

    @NotEmpty(message = "Must not be empty", groups = OnCreate.class)
    @NotBlank
    @NotNull
    private String type;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public interface OnCreate {
    }
}
