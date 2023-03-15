package com.collect.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;


import com.collect.contacts.UI.Login.LoginFragment;

import java.util.Locale;

public class SignActivity extends AppCompatActivity {
	public  String path = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);

		if (getIntent().getBooleanExtra("Crash",false)){
			Log.e("Crash","Crash");
			SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
			String Lang = sp1.getString("Lang", "en");

			SetAppLocale("en");
		}else if (savedInstanceState == null) {
			Log.e("LoginFragment","LoginFragment");
			getSupportFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.newInstance()).commitNow();
		}


	}

	private void SetAppLocale(String localeCode) {
		Log.e("NewLang",localeCode+":");
		Resources resources = getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		Configuration conf = resources.getConfiguration();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			conf.setLocale(new Locale(localeCode));
		} else {
			conf.locale = new Locale(localeCode);
		} resources.updateConfiguration(conf, dm);
		Log.e("LangSign", localeCode);

		Utils.Lang = localeCode;
		Intent intent = new Intent(this, SignActivity.class);
		Log.e("Path : ", path + " kk l ;");
		if (path != null)
			intent.putExtra("ShippingId",path);
		startActivity(intent);
		finish();

	}

}