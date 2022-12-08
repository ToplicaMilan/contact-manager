package com.example.contactmanager.controller.dto;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;

public class ContactDto {

    @NotEmpty(message = "Must not be empty")
    private String firstName;

    @NotEmpty(message = "Must not be empty")
    private String lastName;

    @NotEmpty(message = "Must not be empty")
    private String address;

    @NotEmpty(message = "Must not be empty")
    private String phoneNumber;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
