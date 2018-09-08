package com.psx.roomcontacts.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "contacts_table")
public class Contact {

    @PrimaryKey
    private String phoneNumber;
    @NonNull
    private String firstName;

    private String lastName;

    private String email;

    @Ignore
    public Contact(@NonNull String phoneNumber, @NonNull String firstName) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
    }

    @Ignore
    public Contact(@NonNull String phoneNumber, @NonNull String firstName, @NonNull String lastName) {
        this(phoneNumber, firstName);
        this.lastName = lastName;
    }

    @Ignore
    public Contact(@NonNull String phoneNumber, @NonNull String firstName, @NonNull String lastName, @NonNull String email) {
        this(phoneNumber, firstName, lastName);
        this.email = email;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
