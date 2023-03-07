package com.collect.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



	TextView Contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Contact = findViewById(R.id.Contact);
		Contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goToFragment(new ContactListFragment());

			}
		});
		contextOfApplication = getApplicationContext();





	}
	public static Context contextOfApplication;
	public static Context getContextOfApplication()
	{
		return contextOfApplication;
	}

	public void goToFragment(Fragment fragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.activity_main, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}

}