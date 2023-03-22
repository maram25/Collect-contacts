package com.collect.contacts.UI.Groups;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collect.contacts.MainActivity;
import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Models.GroupsModel;
import com.collect.contacts.R;
import com.collect.contacts.UI.ContactList.ContactListFragment;
import com.collect.contacts.UI.ContactList.Contact_listAdapter;
import com.collect.contacts.UI.GroupsStore.GroupsStoreFragment;
import com.collect.contacts.Utils;

import java.util.List;

public class GroupsFragment extends Fragment {

	private GroupsViewModel mViewModel;


	RelativeLayout Progress;
	ConstraintLayout Layout;
	TextView AddGroup,SendSMS;
	 RecyclerView  GroupsList;
	public static GroupsFragment newInstance() {
		return new GroupsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View root= inflater.inflate(R.layout.groups_fragment, container, false);
		mViewModel = new ViewModelProvider(this).get(GroupsViewModel.class);

	Definations(root);
	Actions();
	Observers();
	SetUP();
	mViewModel.GetGroups();
	StartProgress();
		 return  root;

}

	private void SetUP() {
	}

	private void Observers() {


		mViewModel.ContactList.observe(getViewLifecycleOwner(), new Observer<ContactModel>() {
			@Override
			public void onChanged(ContactModel contactModel) {
				Toast.makeText(getContext(), " send contact success", Toast.LENGTH_LONG).show();

			}
		});

		mViewModel.Groups.observe(getViewLifecycleOwner(), new Observer<List<GroupsModel.Data.Groups>>() {
			@Override
			public void onChanged(List<GroupsModel.Data.Groups> groups) {
				Log.d("groups ","---------" + groups.size());

				final GroupsAdapter adapter2 = new GroupsAdapter(getActivity().getSupportFragmentManager(), getContext(), groups);
				LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
				GroupsList.setLayoutManager(layoutManager2);
				GroupsList.setHasFixedSize(true);
				GroupsList.setAdapter(adapter2);
				 EndProgress();
			}
		});
	}

	private void Actions() {
		checklist_contact.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onClick(View view) {
				Log.e("alllist", true+ "");
				Utils.checkList=true;
				adapter2.notifyDataSetChanged();


				//Utils.Phones=contact;
			}
		});

		SendSMS.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//mViewModel.SendContact(Utils.Sender,Utils.Phones,Utils.SMS);
				mViewModel.SendContact(Utils.Sender, Utils.Phones,Utils.SMS);
			}
		});

		AddGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((MainActivity) getActivity()).goToFragment(new GroupsStoreFragment());

			}
		});
	}

	private void Definations(View root) {

		Progress = root.findViewById(R.id.Progress);
		Layout = root.findViewById(R.id.Layout);
		AddGroup = root.findViewById(R.id.AddGroup);
		GroupsList = root.findViewById(R.id.GroupsList);
		SendSMS = root.findViewById(R.id.SendSMS);
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