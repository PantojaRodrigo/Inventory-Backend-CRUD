package com.pantoja.crudinventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item_location")
public class Location {
    @Id
    @NotNull(message = "is required")
    @Min(value = 1)
    @Column(name = "locationId")
    private int locationId;
    @NotNull(message = "is required")
    @Column(name = "state")
    private String state;
    @Column(name = "address")
    private String address;
    @Column(name = "phoneNumber")
    private String phoneNumber;

    public Location() {
    }

    public Location(int locationId, String state, String address, String phoneNumber) {
        this.locationId = locationId;
        this.state = state;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
