package com.psx.roomcontacts.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.AsyncTask;

import com.psx.roomcontacts.ContactsDatabase;
import com.psx.roomcontacts.dao.ContactsDao;
import com.psx.roomcontacts.entities.Contact;

public class ContactsRepository {

    private ContactsDao contactsDao;
    private LiveData<PagedList<Contact>> allContacts;

    public ContactsRepository(Application application) {
        ContactsDatabase contactsDatabase = ContactsDatabase.getDatabaseInstance(application);
        contactsDao = contactsDatabase.getContactsDao();
        allContacts = new LivePagedListBuilder<>(contactsDao.getAllContacts(), 10).build();
    }

    public void deleteAll() {
        new DeleteAllContactsAsyncTask(contactsDao).execute();
    }

    public void deleteContact(Contact contact) {
        new DeleteContactAsyncTask(contactsDao).execute(contact);
    }

    public void addContact(Contact contact) {
        new AddNewContactAsyncTask(contactsDao).execute(contact);
    }

    public LiveData<PagedList<Contact>> getAllContacts() {
        return allContacts;
    }

    private static class DeleteAllContactsAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContactsDao contactsDao;

        DeleteAllContactsAsyncTask(ContactsDao contactsDao) {
            this.contactsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactsDao.deleteAll();
            return null;
        }
    }

    private static class AddNewContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactsDao contactsDao;

        AddNewContactAsyncTask(ContactsDao contactsDao) {
            this.contactsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDao.insert(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactsDao contactsDao;

        DeleteContactAsyncTask(ContactsDao contactsDao) {
            this.contactsDao = contactsDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactsDao.deleteContact(contacts[0]);
            return null;
        }
    }

}
