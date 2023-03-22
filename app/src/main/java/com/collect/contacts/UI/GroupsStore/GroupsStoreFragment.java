package com.collect.contacts.UI.GroupsStore;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collect.contacts.MainActivity;
import com.collect.contacts.R;
import com.collect.contacts.UI.Groups.GroupsFragment;
import com.collect.contacts.Utils;

import java.util.ArrayList;
import java.util.List;

public class GroupsStoreFragment extends Fragment {

	private GroupsStoreViewModel mViewModel;

	RelativeLayout Progress;
	ConstraintLayout Layout;
	AddPhoneAdapter adapter2;
	 RecyclerView Phones;
	  TextView add_phone,saveGroup;
	   TextView add_phone_from_contact;
	  EditText group_name;
	private ArrayList<String> count = new ArrayList<>();

	private static final int REQUEST_RUNTIME_PERMISSION = 123;
	String[] permissons = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG};


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


		 return  root;

	}

	private void SetUP() {
	    adapter2 = new AddPhoneAdapter(getContext(), count, getActivity().getSupportFragmentManager());
		//adapter2 = new AddPhoneAdapter(getContext(), Utils.GroupsPhones, getActivity().getSupportFragmentManager());
		LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		Phones.setLayoutManager(layoutManager2);
		Phones.setHasFixedSize(true);
		Phones.setAdapter(adapter2);
	}

	private void Observers() {
		mViewModel.tryAgain.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				Toast.makeText(getContext(), "Sorry no valid numbers added..... try Again", Toast.LENGTH_LONG).show();
				EndProgress();

			}
		});
		mViewModel.GroupStore.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				Toast.makeText(getContext(), "The data has been created successfully", Toast.LENGTH_LONG).show();
				((MainActivity) getActivity()).goToFragment(new GroupsFragment());
                EndProgress();

			}
		});
	}

	private void Actions() {
		add_phone.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onClick(View view) {
				count.add(" ");
				adapter2.notifyDataSetChanged();
			}
		});
		saveGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (group_name.getText().toString().equals(" ") || Utils.GroupsPhones.size() == 0) {
					Toast.makeText(getContext(), "Add name and phone numper...", Toast.LENGTH_LONG).show();

				} else {

				mViewModel.GroupS(group_name.getText().toString(), Utils.GroupsPhones);
				StartProgress();
			}
			}
		});

		add_phone_from_contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//if (CheckPermission(Manifest.permission.READ_CONTACTS)) {
					if (CheckPermission(getActivity(), permissons[0])) {
						Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
					   startActivityForResult(intent, REQUEST_RUNTIME_PERMISSION);
				} else {
					// Request Permissions
					RequestPermission(getActivity(), permissons, REQUEST_RUNTIME_PERMISSION);


				}
			}
		});

	}
	public boolean CheckPermission(Context context, String Permission) {
		if (ContextCompat.checkSelfPermission(context,
				Permission) == PackageManager.PERMISSION_GRANTED) {
			return true;
		} else {
			return false;
		}
	}

	public void RequestPermission(Activity thisActivity, String[] Permission, int Code) {
		if (ContextCompat.checkSelfPermission(thisActivity,
				Permission[0])
				!= PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Permission[0])) {
			} else {
				ActivityCompat.requestPermissions(thisActivity, Permission,
						Code);
			}
		}
	}


	private void Definations(View root) {

		Progress = root.findViewById(R.id.Progress);
		Layout = root.findViewById(R.id.Layout);
		Phones = root.findViewById(R.id.Phones);
		add_phone = root.findViewById(R.id.add_phone);
		group_name = root.findViewById(R.id.group_name);
		saveGroup = root.findViewById(R.id.saveGroup);
		add_phone_from_contact = root.findViewById(R.id.add_phone_from_contact);
	}
	public void StartProgress() {
		Progress.setVisibility(View.VISIBLE);
		Layout.setVisibility(View.GONE);
	}

	public void EndProgress() {
		Progress.setVisibility(View.GONE);
		Layout.setVisibility(View.VISIBLE);
	}


	@SuppressLint("NotifyDataSetChanged")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_RUNTIME_PERMISSION) {
			String phoneNo = null;
			String name = null;

			Uri uri = data.getData();
			Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

			if (cursor.moveToFirst()) {
				int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
				int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

				phoneNo = cursor.getString(phoneIndex);
				name = cursor.getString(nameIndex);


				 Utils.from_contact=true;
				Utils.GroupsPhones.add(phoneNo);
				count.add(phoneNo);

				adapter2.notifyDataSetChanged();

				Log.e("List", phoneNo + "");

				Log.e("onActivityResult()", phoneIndex + " " + phoneNo + " " + nameIndex + " " + name);
			}
			cursor.close();
		}
	}
}