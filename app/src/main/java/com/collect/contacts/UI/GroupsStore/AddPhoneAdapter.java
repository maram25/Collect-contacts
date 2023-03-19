package com.collect.contacts.UI.GroupsStore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.collect.contacts.R;


import com.collect.contacts.MainActivity;
import com.collect.contacts.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddPhoneAdapter extends RecyclerView.Adapter<AddPhoneAdapter.ViewHolder>  {

	MainActivity mainActivity;
	FragmentManager fragmentManager;
	Context context;
	private ArrayList<String> Phones = new ArrayList<>();
   String Time;
	int k = 0;
	int mHour;
	int mMinute;
	String date_time = "";

	public AddPhoneAdapter(Context context, ArrayList<String> names, FragmentManager fragmentManager) {
		this.Phones = names;
		this.context = context;
		this.fragmentManager = fragmentManager;
	}

	@NonNull
	@Override
	public AddPhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_phone_item, parent, false);
		AddPhoneAdapter.ViewHolder holder = new AddPhoneAdapter.ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull AddPhoneAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

		holder.delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Phones.remove(position);
				notifyItemRemoved(position);
				notifyItemRangeChanged(position, Phones.size());
//					 Utils.GroupsPhones.remove(position);
//

			}
		});
		holder.Phone.setText(Phones.get(position));
		holder.Phone.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				Phones.add(editable.toString());
				Utils.GroupsPhones.add(editable.toString());
				Log.e("Phone", (holder.Phone.getText()) + "");

			}
		});
//		 Utils.GroupsPhones.add(String.valueOf(holder.Phone.getText()));
//		Log.e("Phone", (holder.Phone.getText()) + "");


	}

	@Override
	public int getItemCount() {
		return Phones.size();
	}
	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView delete;
		EditText Phone;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			delete = itemView.findViewById(R.id.delete);
			Phone = itemView.findViewById(R.id.Phone);

		}
	}
}

