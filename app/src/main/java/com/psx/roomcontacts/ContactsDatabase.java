package com.psx.roomcontacts;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.psx.roomcontacts.dao.ContactsDao;
import com.psx.roomcontacts.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactsDatabase extends RoomDatabase {
    private static ContactsDatabase INSTANCE;
    private static final String TAG = ContactsDatabase.class.getSimpleName();

    public static ContactsDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactsDatabase.class, "contacts_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    new AddPersonalContactInfoAsyncTask(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ContactsDao getContactsDao();

    static class AddPersonalContactInfoAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContactsDao contactsDao;

        AddPersonalContactInfoAsyncTask(ContactsDatabase database) {
            contactsDao = database.getContactsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (contactsDao.getAnyContact().length <= 0) {
                Contact contact = new Contact("9161986851", "Pranav", "Sharma", "pranav.ps95@hotmail.com");
                Log.d(TAG, "Inserting new Contact");
                contactsDao.insert(contact);
            } else {
                Log.d(TAG, "Personal Contact Already registered");
            }
            return null;
        }
    }
}
