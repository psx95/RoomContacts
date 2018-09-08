package com.psx.roomcontacts.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.psx.roomcontacts.entities.Contact;

@Dao
public interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Contact contact);

    @Query("DELETE FROM contacts_table")
    void deleteAll();

    @Query("SELECT * FROM contacts_table LIMIT 1")
    Contact[] getAnyContact();

    @Delete
    void deleteWord();

    @Query("SELECT * FROM contacts_table WHERE phoneNumber = :phoneNumber")
    Contact getContactFromPhoneNumber(String phoneNumber);

    @Query("SELECT * FROM contacts_table ORDER BY firstName ASC")
    DataSource.Factory<String, Contact> getAllContacts();

    @Query("SELECT * FROM contacts_table ORDER BY lastName ASC")
    DataSource.Factory<String, Contact> getAllContactsByLastName();

}
