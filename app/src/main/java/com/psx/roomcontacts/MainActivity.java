package com.psx.roomcontacts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.psx.roomcontacts.Adapters.ContactsPagedAdapter;
import com.psx.roomcontacts.entities.Contact;
import com.psx.roomcontacts.viewmodel.ContactsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton addContactsButton;
    @BindView(R.id.recyclerview)
    RecyclerView contactsRecyclerView;

    ContactsViewModel contactsViewModel;
    ContactsPagedAdapter contactsPagedAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contactsViewModel = ViewModelProviders.of(MainActivity.this).get(ContactsViewModel.class);
        setupRecyclerView();
        addObserversForLiveData();
    }

    private void addObserversForLiveData() {
        contactsViewModel.allContacts.observe(this, new Observer<PagedList<Contact>>() {
            @Override
            public void onChanged(@Nullable PagedList<Contact> contacts) {
                Log.d(TAG, "OnChnaged Called - Data changed");
                contactsPagedAdapter.setContacts(contacts);
            }
        });
    }

    private void setupRecyclerView() {
        contactsPagedAdapter = new ContactsPagedAdapter(new RecyclerViewOnClickListener() {

            @Override
            public void onContactClick(Contact contact) {

            }

            @Override
            public void notifyActivityOfZeroContacts() {

            }

            @Override
            public void notifyActivityOfNonZeroContacts() {

            }
        });
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactsPagedAdapter);
    }

    public interface RecyclerViewOnClickListener {
        void onContactClick(Contact contact);

        void notifyActivityOfZeroContacts();

        void notifyActivityOfNonZeroContacts();
    }
}
