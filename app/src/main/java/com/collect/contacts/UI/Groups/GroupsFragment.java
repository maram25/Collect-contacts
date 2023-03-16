package com.collect.contacts.UI.Groups;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.collect.contacts.MainActivity;
import com.collect.contacts.Models.GroupsModel;
import com.collect.contacts.R;
import com.collect.contacts.UI.ContactList.ContactListFragment;
import com.collect.contacts.UI.ContactList.Contact_listAdapter;
import com.collect.contacts.UI.GroupsStore.GroupsStoreFragment;

import java.util.List;

public class GroupsFragment extends Fragment {

	private GroupsViewModel mViewModel;


	RelativeLayout Progress;
	ConstraintLayout Layout;
	TextView AddGroup;
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