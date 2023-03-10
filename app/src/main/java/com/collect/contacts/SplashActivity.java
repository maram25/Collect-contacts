package com.collect.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
	ImageView companyLogo,alarm_graphic;
	LinearLayout x;
	TextView English, Arabic;

	String path = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
//		companyLogo = findViewById(R.id.companyLogo);
		x = findViewById(R.id.linearLayout2);
		Arabic = findViewById(R.id.Arabic);
		English = findViewById(R.id.English);


		Uri uri = getIntent().getData();
		if (uri!=null) {
			path = uri.getQueryParameter("medicine_id");
			Log.e("path",  " "+path);
			/*Intent intent = getIntent();
			String action = intent.getAction();
			Uri data = intent.getData();
			Log.e("data",  " "+data);*/
			Log.e("data id",  " "+uri.getQueryParameter("medicine_id"));

		}

//		Animation company_logo = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom_animation);
//		Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top_animation);
//		Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
//		companyLogo.startAnimation(company_logo);
//		x.startAnimation(animation);
//		alarm_graphic.startAnimation(rotate);

		SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
		String Lang = sp1.getString("Lang", null);
		if (Lang != null) {
			x.setVisibility(View.GONE);
			new CountDownTimer(2000, 1000) {
				public void onTick(long millisUntilFinished) {
				}
				public void onFinish() {
					SetAppLocale(Lang);
				}
			}.start();
		}
		English.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetAppLocale("en");
				Log.e("Lang", Utils.Lang + "   llll");
			}
		});
		Arabic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetAppLocale("ar");
				Log.e("Lang", Utils.Lang + "   llll");

			}
		});



	}
	/*	public void goToFragment(Fragment fragment) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.nav_host_fragment, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}*/
	public SharedPreferences sp;
	public SharedPreferences.Editor Ed;



	private void SetAppLocale(String localeCode) {
		Resources resources = getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		Configuration conf = resources.getConfiguration();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			conf.setLocale(new Locale(localeCode));
		} else {
			conf.locale = new Locale(localeCode);
		}
		sp = getSharedPreferences("Login", MODE_PRIVATE);
		SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
		Ed = sp.edit();
		Ed.putString("Lang", localeCode);

		Ed.commit();
//        mViewHolder.mDuoDrawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
		resources.updateConfiguration(conf, dm);
		Utils.Lang = localeCode;
		Intent intent = new Intent(this, SignActivity.class);
		if (path != null) intent.putExtra("ShippingId",path);
		startActivity(intent);
		finish();

	}
}