package com.collect.contacts.UI.GroupsStore;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collect.contacts.R;

import java.util.ArrayList;

public class GroupsStoreFragment extends Fragment {

	private GroupsStoreViewModel mViewModel;

	RelativeLayout Progress;
	ConstraintLayout Layout;
	AddPhoneAdapter adapter2;
	 RecyclerView Phones;
	  TextView add_phone;
	private ArrayList<String> count = new ArrayList<>();

	public static GroupsStoreFragment newInstance() {
		return new GroupsStoreFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		if (container != null) {
			container.removeAllViews();
		}
		View root= inflater.inflate(R.layout.groups_store_fragment, container, false);
		mViewModel = new ViewModelProvider(this).get(GroupsStoreViewModel.class);
		Definations(root);
		Actions();
		Observers();
		SetUP();
		StartProgress();


		 return  root;

	}

	private void SetUP() {

		adapter2 = new AddPhoneAdapter(getContext(), count, getActivity().getSupportFragmentManager());
		LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		Phones.setLayoutManager(layoutManager2);
		Phones.setHasFixedSize(true);
		Phones.setAdapter(adapter2);
	}

	private void Observers() {
	}

	private void Actions() {
		add_phone.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onClick(View view) {
				count.add("100");
				adapter2.notifyDataSetChanged();
			}
		});


	}

	private void Definations(View root) {

		Progress = root.findViewById(R.id.Progress);
		Layout = root.findViewById(R.id.Layout);
		Phones = root.findViewById(R.id.Phones);
		add_phone = root.findViewById(R.id.add_phone);
	}
	public void StartProgress() {
		Progress.setVisibility(View.VISIBLE);
		Layout.setVisibility(View.GONE);
	}

	public void EndProgress() {
		Progress.setVisibility(View.GONE);
		Layout.setVisibility(View.VISIBLE);
	}
}