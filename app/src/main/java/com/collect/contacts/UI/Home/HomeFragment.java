package com.collect.contacts.UI.Home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collect.contacts.MainActivity;
import com.collect.contacts.Models.UserProfileModel;
import com.collect.contacts.R;
import com.collect.contacts.UI.ContactList.ContactListFragment;

public class HomeFragment extends Fragment {

	private HomeViewModel mViewModel;
	TextView Contact;

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		if (container != null) {
			container.removeAllViews();
		}
		View root= inflater.inflate(R.layout.home_fragment, container, false);
		mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

		Definations(root);
		Actions();
		Observers();
		SetUP();
		 mViewModel.UserProfile();

		 return  root;

	}

	private void SetUP() {
	}

	private void Observers() {

		mViewModel.User.observe(getViewLifecycleOwner(), new Observer<UserProfileModel.Data.User>() {
			@Override
			public void onChanged(UserProfileModel.Data.User user) {



			}
		});
	}

	private void Actions() {
		Contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((MainActivity) getActivity()).goToFragment(new ContactListFragment());


			}
		});
	}

	private void Definations(View root) {
		Contact = root.findViewById(R.id.Contact);

	}

}