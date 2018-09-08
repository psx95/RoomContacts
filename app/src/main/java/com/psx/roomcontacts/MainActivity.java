package com.psx.roomcontacts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.psx.roomcontacts.viewmodel.ContactsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton addContactsButton;
    @BindView(R.id.recyclerview)
    RecyclerView contactsRecyclerView;

    ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contactsViewModel = ViewModelProviders.of(MainActivity.this).get(ContactsViewModel.class);
        addObserversForLiveData();
        setupRecyclerView();
    }

    private void addObserversForLiveData() {
    }

    private void setupRecyclerView() {

    }
}
