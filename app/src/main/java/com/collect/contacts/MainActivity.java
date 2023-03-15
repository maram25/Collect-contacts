package com.collect.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.collect.contacts.UI.ContactList.ContactListFragment;
import com.collect.contacts.UI.Home.HomeFragment;


public class MainActivity extends AppCompatActivity {

	public SharedPreferences sp;
	public SharedPreferences.Editor Ed;
	DrawerLayout drawer;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new HomeFragment()).commit();
		}
		SetUp();
		drawer = findViewById(R.id.drawer_layout);

	}



	private void SetUp() {
		SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
		Utils.User_Login = sp.getBoolean("User_Login", false);
		Utils.Lang = sp.getString("Lang", null);
		Log.d("User_Login ",Utils.User_Login +"" );
		Utils.Token = sp.getString("Token", null);
		Ed = sp.edit();
		Ed.putString("Lang",Utils.Lang);
		Ed.commit();
		Log.d("Lang ",Utils.Lang +"" );

		if (Utils.User_Login) {
			goToFragment(new HomeFragment());

		}
	}


	public void goToFragment(Fragment fragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.nav_host_fragment_activity_main, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}
}