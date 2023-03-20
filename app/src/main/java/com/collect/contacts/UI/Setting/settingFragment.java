package com.collect.contacts.UI.Setting;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.collect.contacts.MainActivity;
import com.collect.contacts.Models.UserProfileModel;
import com.collect.contacts.R;
import com.collect.contacts.SignActivity;
import com.collect.contacts.UI.AboutUS.AboutUSFragment;
import com.collect.contacts.UI.ContactList.ContactListFragment;
import com.collect.contacts.UI.GroupsStore.GroupsStoreFragment;
import com.collect.contacts.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Locale;

public class settingFragment extends Fragment {

	private SettingViewModel mViewModel;
	 RelativeLayout LOGOUT,lang,aboutUs;
	 public SharedPreferences sp;
	public SharedPreferences.Editor Ed;
	RelativeLayout Progress;
	ConstraintLayout Layout;


	public static settingFragment newInstance() {
		return new settingFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {

		if (container != null) {
			container.removeAllViews();
		}
		View root= inflater.inflate(R.layout.setting_fragment, container, false);
		mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);



		Definations(root);
		Actions();
		Observers();
		SetUP();

		return  root;

	}

	private void SetUP() {
	}

	private void Observers() {


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


		LOGOUT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mViewModel.lOGOUT();
				StartProgress();
			}
		});
		aboutUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((MainActivity) getActivity()).goToFragment(new AboutUSFragment());

			}
		});
		lang.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (Utils.Lang.equals("en"))
					SetAppLocale("ar");
				else
					SetAppLocale("en");
			}
		});

	}

	private void Definations(View root) {
		Progress = root.findViewById(R.id.Progress);
		Layout = root.findViewById(R.id.Layout);

		LOGOUT = root.findViewById(R.id.LOGOUT);
		aboutUs = root.findViewById(R.id.aboutUs);
		lang = root.findViewById(R.id.lang);


	}
	public void StartProgress() {
		Progress.setVisibility(View.VISIBLE);
		Layout.setVisibility(View.GONE);
	}

	public void EndProgress() {
		Progress.setVisibility(View.GONE);
		Layout.setVisibility(View.VISIBLE);
	}

	public void SetAppLocale(String localeCode) {
		Resources resources = getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		Configuration conf = resources.getConfiguration();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			conf.setLocale(new Locale(localeCode));
		} else {
			conf.locale = new Locale(localeCode);
		}
		resources.updateConfiguration(conf, dm);

		sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
		SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
		Ed = sp.edit();
		Ed.putString("Lang", localeCode);

		Ed.commit();
		Utils.Lang = localeCode;
		Intent intent = new Intent(getContext(), MainActivity.class);
		startActivity(intent);
		getActivity().finish();

	}




}