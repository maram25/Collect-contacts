package com.collect.contacts.UI.Home;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collect.contacts.MainActivity;
import com.collect.contacts.Models.UserProfileModel;
import com.collect.contacts.R;
import com.collect.contacts.SignActivity;
import com.collect.contacts.UI.ContactList.ContactListFragment;
import com.collect.contacts.UI.Groups.GroupsFragment;
import com.collect.contacts.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

	private HomeViewModel mViewModel;
	TextView Contact,available,contacts,Groups;
	 EditText SMS;
	RelativeLayout Progress;
	ConstraintLayout Layout;
	// EditText SMSEditText;
	MaterialSpinner  sender;
	//TextView SMSTextView;
	private TextInputEditText SMSEditText;
    private TextInputLayout SMSTextView;
	public SharedPreferences sp;
	public SharedPreferences.Editor Ed;
	 TextWatcher mTextEditorWatcher ;
	List<Integer> SenderId = new ArrayList<>();
	List<String> SenderN = new ArrayList<>();

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
		  StartProgress();

		 return  root;

	}

	private void SetUP() {
	}

	private void Observers() {

		mViewModel.User.observe(getViewLifecycleOwner(), new Observer<UserProfileModel.Data.User>() {
			@Override
			public void onChanged(UserProfileModel.Data.User user) {
				available.setText(getResources().getString(R.string.balance_messages)+"\n \n"+user.getBalance()+"");
				SenderId = new ArrayList<>();
				SenderN = new ArrayList<>();
				for (int i = 0; i < user.getSender_ids().size(); i++) {
					SenderN.add(user.getSender_ids().get(i).getName());
					//SenderId.add(times.get(i).getId());
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, SenderN);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sender.setAdapter(adapter);
				 EndProgress();


			}
		});
		mViewModel.tryAgain.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				mViewModel.lOGOUT();
			}
		});
		mViewModel.LOGOUT.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
				Ed = sp.edit();
				Ed.putString("E_mail", null);
				Ed.putString("Phone", null);
				Ed.putString("Psw", null);
				Ed.putString("Name", null);
				Ed.putString("Lang", Utils.Lang);
				Ed.putString("Token", null);
				Utils.User_Login= false;
				Ed.putBoolean("User_Login",false );
				Ed.commit();
				Intent i = new Intent(getActivity(), SignActivity.class);
				i.putExtra("User_Login", false);
				startActivity(i);
				getActivity().finish();
				EndProgress();


			}
		});
	}

	private void Actions() {

		 mTextEditorWatcher = new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//This sets a textview to the current length
				//SMSTextView.setTextAlignment(250-s.length());
				if (s.equals('\t') ) {
					Log.d("tab","---------" );

					SMSTextView.setTextAlignment( count+2);
				}
			}

			public void afterTextChanged(Editable s) {
			}
		};

		 ///// close emoji  from KP

		SMSEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

		SMSEditText.addTextChangedListener(mTextEditorWatcher);


		sender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
            Utils.Sender=item.toString();
				Log.d("Sender","---------" + item.toString());

			}
		});

		Contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				 if (Utils.Sender==null|| SMSEditText== null)
				 {
					 Toast.makeText(getContext(), "check for choose user sender and add SMS", Toast.LENGTH_LONG).show();

				 }else
				 	 Utils.SMS=SMSEditText.getText().toString();
			     	((MainActivity) getActivity()).goToFragment(new ContactListFragment());
			}
		});
		contacts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				 if (Utils.Sender==null|| SMSEditText== null)
				 {
					 Toast.makeText(getContext(), "check for choose user sender and add SMS", Toast.LENGTH_LONG).show();

				 }else
				 	 Utils.SMS=SMSEditText.getText().toString();
			     	((MainActivity) getActivity()).goToFragment(new ContactListFragment());
			}
		});
		Groups.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				 if (Utils.Sender==null|| SMSEditText== null)
				 {
					 Toast.makeText(getContext(), "check for choose user sender and add SMS", Toast.LENGTH_LONG).show();

				 }else
				 	 Utils.SMS=SMSEditText.getText().toString();
			     	((MainActivity) getActivity()).goToFragment(new GroupsFragment());
			}
		});
	}

	private void Definations(View root) {
		Progress = root.findViewById(R.id.Progress);
		Layout = root.findViewById(R.id.Layout);

		Contact = root.findViewById(R.id.Contact);
		contacts = root.findViewById(R.id.contacts);
		Groups = root.findViewById(R.id.Groups);
		sender = root.findViewById(R.id.sender);
		available = root.findViewById(R.id.available);
		SMSTextView = (TextInputLayout) root.findViewById(R.id.SMSTextView);
		SMSEditText = (TextInputEditText)  root.findViewById(R.id.SMSEditText);

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