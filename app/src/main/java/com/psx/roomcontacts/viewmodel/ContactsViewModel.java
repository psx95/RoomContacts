package com.psx.roomcontacts.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.psx.roomcontacts.entities.Contact;
import com.psx.roomcontacts.repository.ContactsRepository;

public class ContactsViewModel extends AndroidViewModel {

    private ContactsRepository contactsRepository;

    public LiveData<PagedList<Contact>> allContacts;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        contactsRepository = new ContactsRepository(application);
        allContacts = contactsRepository.getAllContacts();
    }

    public void addNewContact(Contact contact) {
        contactsRepository.addContact(contact);
    }

    public void deleteContact(Contact contact) {
        contactsRepository.deleteContact(contact);
    }
}
