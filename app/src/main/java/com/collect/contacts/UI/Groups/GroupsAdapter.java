package com.collect.contacts.UI.Groups;

import android.content.Context;
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

import com.collect.contacts.MainActivity;
import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Models.GroupsModel;
import com.collect.contacts.R;
import com.collect.contacts.Utils;

import java.util.ArrayList;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder>{

    FragmentManager fragmentManager;

    List<GroupsModel.Data.Groups> Groups = new ArrayList<>();
    Context context;
    MainActivity activity;

    public GroupsAdapter(FragmentManager fragmentManager, Context context, List<GroupsModel.Data.Groups> Groups) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.Groups = Groups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (Utils.checkList)
        {
            holder.check_group_item.setChecked(true);
        }
        else
            holder.check_group_item.setChecked(false);

        holder.name.setText( Groups.get(position).getName()+"");
       holder.number.setText( Groups.get(position).getCount_phones()+"");
        Utils.Phones=Groups.get(position).getPhones();

holder.check_group_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Utils.selected_itemsGroupsPhones.add( Groups.get(position).getCount_phones());
    }
});
    }




    @Override
    public int getItemCount() {
        return Groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView number,name;
         ConstraintLayout item;
          CheckBox check_group_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
            name = itemView.findViewById(R.id.name);
            check_group_item = itemView.findViewById(R.id.check_group_item);

        }
    }
}
