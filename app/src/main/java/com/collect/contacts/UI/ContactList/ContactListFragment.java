package com.collect.contacts.UI.ContactList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.MainActivity;
import com.collect.contacts.R;
import com.collect.contacts.Utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment {

	private ContactListViewModel mViewModel;
	 RecyclerView list_item;
	  TextView SendSMS;
	  TextView checklist_contact;
	 //  ImageView checklist_contact;
	    Boolean alllist=false;
	List<String> contact = new ArrayList<>();
	Contact_listAdapter adapter2;

	ConstraintLayout Layout;
	RelativeLayout Progrees;

	public static ArrayList<ContactModel> contactList = new ArrayList<>();

	private static final int REQUEST_RUNTIME_PERMISSION = 123;
	String[] permissons = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG};

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
		Observers();
		Actions();

//		ContactModel contactModel=new ContactModel();
//		contactModel.setEmail("test");
//		contactModel.setPhone("011535985");
//		contactModel.setName("maram");
//		contactList.add(contactModel);

		contact.add("01153919837");




//		final Contact_listAdapter adapter2 = new Contact_listAdapter(getActivity().getSupportFragmentManager(), getContext(), contactList);
//		LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//		list_item.setLayoutManager(layoutManager2);
//		list_item.setHasFixedSize(true);
//		list_item.setAdapter(adapter2);
//		Log.e("List", contactList.size() + "");


		if (CheckPermission(getActivity(), permissons[0])) {
			 StartProgress();
			GetContactFromDevice getContactFromDevice = new GetContactFromDevice();

			 adapter2 = new Contact_listAdapter(getActivity().getSupportFragmentManager(), getContext(), getContactFromDevice.getContacts(getContext()));
			LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
			list_item.setLayoutManager(layoutManager2);
			list_item.setHasFixedSize(true);
			list_item.setAdapter(adapter2);
			 EndProgress();

			//mViewModel.SendContact();
			Log.e("List", contactList.size() + "");

		} else {
			// you do not have permission go request runtime permissions
			RequestPermission(getActivity(), permissons, REQUEST_RUNTIME_PERMISSION);
			 EndProgress();
		}


		return  root;
	}

	private void Actions() {
		checklist_contact.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NotifyDataSetChanged")
			@Override
			public void onClick(View view) {
				Log.e("alllist", true+ "");
				alllist=true;
				 Utils.checkList=true;
				adapter2.notifyDataSetChanged();


				//Utils.Phones=contact;
			}
		});
		 SendSMS.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {
			 	 if (alllist) {
					 //mViewModel.SendContact(Utils.Sender,Utils.Phones,Utils.SMS);
					 mViewModel.SendContact(Utils.Sender, contact, Utils.SMS);
					 alllist = false;
					 Utils.checkList=false;


				 }
			 	 else{
					 mViewModel.SendContact(Utils.Sender,Utils.selected_items_phones, Utils.SMS);
				 }

			 }
		 });
	}

		private void Observers() {


		mViewModel.ContactList.observe(getViewLifecycleOwner(), new Observer<ContactModel>() {
			@Override
			public void onChanged(ContactModel contactModel) {
            Toast.makeText(getContext(), " send contact success", Toast.LENGTH_LONG).show();

			}
		});
	}

	private void Definations(View root) {
		Layout = root.findViewById(R.id.Layout);
		Progrees = root.findViewById(R.id.Progress);
		list_item = root.findViewById(R.id.list_item);
		SendSMS = root.findViewById(R.id.SendSMS);
		checklist_contact = root.findViewById(R.id.checklist_contact);

	}
	private void collect() {


	}

	public class GetContactFromDevice {
		private static final String TAG  = "GetContactFromDevice";
		@SuppressLint("Range")
		public ArrayList<ContactModel> getContacts(Context context) {
			ArrayList<ContactModel> list = new ArrayList<>();
			ContentResolver contentResolver = context.getContentResolver();
			Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					@SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
					if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
						Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
						InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
								ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

						Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
						Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

						Bitmap photo = null;
						if (inputStream != null) {
							photo = BitmapFactory.decodeStream(inputStream);
						}
						while (cursorInfo.moveToNext()) {
							ContactModel info = new ContactModel();
							info.setName( cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
							info.setPhone(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
							Utils.Phones.add(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
							//info.setEmail(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
							info.setImage( pURI.toString());
							list.add(info);


							EndProgress();

							Log.d("GetContactFromDevice", "getContacts: " + info.getName());
							Log.d("GetContactFromDevice", "getContacts: " + info.getPhone());

						//	Log.d("GetContactFromDevice", "getEmail: " + info.getEmail());
						}
						cursorInfo.close();
					}
				}
				cursor.close();
			}
			return list;
		}

	}


	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
		switch (permsRequestCode) {

			case REQUEST_RUNTIME_PERMISSION: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// you have permission go ahead
//					GetContactFromDevice getContactFromDevice = new GetContactFromDevice();
//					final Contact_listAdapter adapter2 = new Contact_listAdapter(getActivity().getSupportFragmentManager(), getContext(), getContactFromDevice.getContacts(getContext()));
//					LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//					list_item.setLayoutManager(layoutManager2);
//					list_item.setHasFixedSize(true);
//					list_item.setAdapter(adapter2);
					Log.e("ListRequest", contactList.size() + "");
				} else {
					// you do not have permission show toast.
				}
				return;
			}
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

	public boolean CheckPermission(Context context, String Permission) {
		if (ContextCompat.checkSelfPermission(context,
				Permission) == PackageManager.PERMISSION_GRANTED) {
			return true;
		} else {
			return false;
		}
	}

	public void StartProgress() {
		Progrees.setVisibility(View.VISIBLE);
		Layout.setVisibility(View.GONE);
	}

	public void EndProgress() {
		Progrees.setVisibility(View.GONE);
		Layout.setVisibility(View.VISIBLE);
	}

}