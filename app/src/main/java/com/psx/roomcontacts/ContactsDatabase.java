package com.psx.roomcontacts;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.psx.roomcontacts.dao.ContactsDao;
import com.psx.roomcontacts.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactsDatabase extends RoomDatabase {
    private static ContactsDatabase INSTANCE;

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
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ContactsDao getContactsDao();
}
