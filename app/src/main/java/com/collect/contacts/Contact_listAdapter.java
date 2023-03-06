package com.collect.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class Contact_listAdapter extends RecyclerView.Adapter<Contact_listAdapter.ViewHolder>{

    FragmentManager fragmentManager;

    List<ContactModel> Contact = new ArrayList<>();
    Context context;
    MainActivity activity;


    public Contact_listAdapter( FragmentManager fragmentManager, Context context, List<ContactModel> Contact) {
        this.context = context;
        this.fragmentManager = fragmentManager;

        this.Contact = Contact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contant_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

   holder.name.setText( Contact.get(position).getName()+"");
   holder.phone.setText( Contact.get(position).getPhone()+"");
   holder.email.setText( Contact.get(position).getEmail()+"");


    }


    @Override
    public int getItemCount() {
        return Contact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView phone,email,name;
         ConstraintLayout item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            phone = itemView.findViewById(R.id.phone);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);

        }
    }
}
