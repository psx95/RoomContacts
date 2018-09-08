package com.psx.roomcontacts.Adapters;

import android.arch.paging.PagedListAdapter;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.psx.roomcontacts.MainActivity.RecyclerViewOnClickListener;
import com.psx.roomcontacts.R;
import com.psx.roomcontacts.entities.Contact;

import java.util.List;

public class ContactsPagedAdapter extends PagedListAdapter<Contact, ContactsPagedAdapter.ContactsViewHolder> {

    private List<Contact> contacts;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;
    private ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    public ContactsPagedAdapter(RecyclerViewOnClickListener recyclerViewOnClickListener) {
        super(DIFF_CALLBACK);
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_rv, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        if (contacts != null) {
            final Contact contact = contacts.get(position);
            String contactName = contact.getFirstName() + " " + contact.getLastName();
            String initials = "" + contact.getFirstName().charAt(0) + contact.getLastName().charAt(0);
            TextDrawable contactPicture = TextDrawable.builder().buildRound(initials, colorGenerator.getRandomColor());
            holder.name.setText(contactName);
            holder.contactImage.setImageDrawable(contactPicture);
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewOnClickListener.onContactClick(contact);
                }
            });
        }
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            if (contacts.size() == 1)
                recyclerViewOnClickListener.notifyActivityOfNonZeroContacts();
            return contacts.size();
        } else {
            recyclerViewOnClickListener.notifyActivityOfZeroContacts();
            return 0;
        }
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView contactImage;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            contactImage = itemView.findViewById(R.id.contact_pic);
        }
    }

    private static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(Contact oldItem, Contact newItem) {
            return oldItem.getPhoneNumber().equals(newItem.getPhoneNumber());
        }

        @Override
        public boolean areContentsTheSame(Contact oldItem, Contact newItem) {
            if (oldItem.getPhoneNumber().equals(newItem.getPhoneNumber()))
                if (oldItem.getFirstName().equals(newItem.getFirstName()))
                    if (oldItem.getLastName().equals(newItem.getLastName()))
                        return oldItem.getEmail().equals(newItem.getEmail());
            return false;
        }
    };
}
