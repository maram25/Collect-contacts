package com.collect.contacts.UI.ContactList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.MainActivity;
import com.collect.contacts.R;
import com.collect.contacts.Utils;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

         if (Utils.checkList)
         {
             holder.check_contact_item.setChecked(true);
         }
          else
             holder.check_contact_item.setChecked(false);


        holder.name.setText( Contact.get(position).getName()+"");
     holder.phone.setText( Contact.get(position).getPhone()+"");
    holder.check_contact_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


            if(holder.check_contact_item.isChecked()){
                Utils.selected_items_phones.add(Contact.get(position).getPhone());


            }
            else
            {
                Utils.selected_items_phones.remove(Contact.get(position).getPhone());
                Log.d("selected_items_phones","---------" + Contact.get(position).getPhone());

            }


        }
    });
    }

    @Override
    public int getItemCount() {
        return Contact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView phone,name;
         ConstraintLayout item;
        CheckBox check_contact_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            phone = itemView.findViewById(R.id.phone);
            name = itemView.findViewById(R.id.name);
            check_contact_item = itemView.findViewById(R.id.check_contact_item);

        }
    }
}
