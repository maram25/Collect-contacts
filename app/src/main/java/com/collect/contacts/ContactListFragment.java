package com.collect.contacts;

import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactListFragment extends Fragment {

	private ContactListViewModel mViewModel;
	 RecyclerView list_item;

	public static ArrayList<ContactModel> contactList = new ArrayList<>();


	public static ContactListFragment newInstance() {
		return new ContactListFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
			if (container != null) {
				container.removeAllViews();
			}
		View root= inflater.inflate(R.layout.contact_list_fragment, container, false);
		mViewModel = new ViewModelProvider(this).get(ContactListViewModel.class);

		Definations(root);
		collect();
		Log.e("List", contactList + "");



		final Contact_listAdapter adapter2 = new Contact_listAdapter(getActivity().getSupportFragmentManager(), getContext(), contactList);
		LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		list_item.setLayoutManager(layoutManager2);
		list_item.setHasFixedSize(true);
		list_item.setAdapter(adapter2);




		return  root;
	}

	private void Definations(View root) {
		list_item = root.findViewById(R.id.list_item);

	}
	private void collect() {
	}




}