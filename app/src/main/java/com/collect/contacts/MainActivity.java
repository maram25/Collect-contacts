package com.collect.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.LoginModel;
import com.collect.contacts.UI.ContactList.ContactListFragment;
import com.collect.contacts.UI.Groups.GroupsFragment;
import com.collect.contacts.UI.Home.HomeFragment;
import com.collect.contacts.UI.Login.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

	public SharedPreferences sp;
	public SharedPreferences.Editor Ed;
	DrawerLayout drawer;
	BottomNavigationView bottomNavigationView;



	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new HomeFragment()).commit();
		}
		SetUp();

		Definations();

	}
	private void Definations() {
		drawer = findViewById(R.id.drawer_layout);
		bottomNavigationView  = findViewById(R.id.nav_view_bottom);
		BottomNavigationView navView = findViewById(R.id.nav_view_bottom);
		navView.setOnNavigationItemSelectedListener(navListener);


	}

		BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			Fragment selectedFragment = null;
			switch (item.getItemId()) {
				case R.id.navigation_home:
					 goToFragment( new HomeFragment());
					//selectedFragment = new HomeFragment();
					break;
				case R.id.LOGOUT:
					lOGOUT();
					break;
				case R.id.Groups:
					goToFragment( new GroupsFragment());

					//selectedFragment = new GroupsFragment();
					break;

			}
			//getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, selectedFragment).commit();
			return true;
		}


	};



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

	public void lOGOUT() {

		APIInterface apiInterface;
		MutableLiveData<Boolean> LOGOUT = new MutableLiveData<>();
		apiInterface = APIClient.getClient().create(APIInterface.class);
		Call<LoginModel> call = apiInterface.logout(Utils.Token);
		call.enqueue(new Callback<LoginModel>() {
			@Override
			public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
				if (response.isSuccessful()) {
					LOGOUT.setValue(true);


					sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
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
					Intent i = new Intent(getApplicationContext(), SignActivity.class);
					i.putExtra("User_Login", false);
					startActivity(i);
					finish();
				} else
					LOGOUT.setValue(true);

			}

			@Override
			public void onFailure(Call<LoginModel> call, Throwable t) {

			}
		});
	}
}