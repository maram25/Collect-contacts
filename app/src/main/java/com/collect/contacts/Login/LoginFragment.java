package com.collect.contacts.Login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.collect.contacts.MainActivity;
import com.collect.contacts.SignActivity;
import com.collect.contacts.Utils;
import com.collect.contacts.R;


public class LoginFragment extends Fragment {

	private LoginViewModel mViewModel;


	TextView ForgetPassword;
	String path;

	EditText EmailAddress;

	TextView SignUp, Login, Skip_Registration, Send_now;
	ConstraintLayout ForgetPassPopup;
	EditText phone, Password;
	SignActivity signActivity;
	ConstraintLayout Layout;
	RelativeLayout Progrees;



	public static LoginFragment newInstance() {
		return new LoginFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		if (container != null) {
			container.removeAllViews();
		}
		View root = inflater.inflate(R.layout.login_fragment, container, false);
		mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


		signActivity = (SignActivity) getActivity();

		Definations(root);
		Actions();
		Observers();
		SetUP();


		return root;

	}

	private void SetUP() {

		SharedPreferences sp1 = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
		String Token = sp1.getString("Token", null);
		String Lang = sp1.getString("Lang", "en");
		String Name = sp1.getString("Name", null);
		String Phone = sp1.getString("Phone", null);
		boolean User_Login = sp1.getBoolean("User_Login", true);
		Log.e("Login", Token + "");

		if (Token != null) {
			Utils.Token = Token;
			Utils.Lang = Lang;
			Utils.NameF = Name;
			Utils.NameL = Name;
			Utils.Phone = Phone;
			Utils.User_Login = User_Login;
			Intent intent = new Intent(getContext(), MainActivity.class);
			if (path != null)
			startActivity(intent);
			getActivity().finish();
		}
	}

	private void Observers() {

		mViewModel.Login.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (aBoolean) {
					Intent i = new Intent(getContext(), MainActivity.class);
					Utils.User_Login = true;
					i.putExtra("User_Login", true);
					startActivity(i);
					getActivity().finish();
					EndProgress();
				}
			}
		});
		mViewModel.LoginError.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (getContext() != null)
					// Toast.makeText(getContext(), R.string.LoginError, Toast.LENGTH_LONG).show();
					if (Utils.Lang == "en") {
						// StyleableToast.makeText(getContext(), "Invalid Data", R.style.invalidexampleToast).show();
					} else {
						//  StyleableToast.makeText(getContext(), "بينات غير صحيحة", R.style.invalidexampleToast).show();
					}
				EndProgress();

			}
		});
		mViewModel.EmailNotValid.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (getContext() != null)
				//	Toast.makeText(getContext(), R.string.EmailNotValid, Toast.LENGTH_LONG).show();
				EndProgress();

			}
		});
		mViewModel.EmailError.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (getContext() != null)
				//	Toast.makeText(getContext(), R.string.EmailError, Toast.LENGTH_LONG).show();
				EndProgress();

			}
		});
		mViewModel.PasswordError.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (getContext() != null)
					//Toast.makeText(getContext(), R.string.PasswordError, Toast.LENGTH_LONG).show();
				EndProgress();

			}
		});
		mViewModel.verifiedError.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				if (getContext() != null)
					//Toast.makeText(getContext(), R.string.verifiedError, Toast.LENGTH_LONG).show();

				// Utils.VerifyPhone = Phone.getText().toString();

				EndProgress();
			}
		});
//		mViewModel.Forget_pass.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//			@Override
//			public void onChanged(Boolean aBoolean) {
//				goToFragment(new ResetPasswordFragment());
//				EndProgress();
//			}
//		});


		mViewModel.SomeThingWrong.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
			@Override
			public void onChanged(Boolean aBoolean) {
				//Toast.makeText(getContext(), R.string.EmailNotValid, Toast.LENGTH_LONG).show();
                EndProgress();
			}
		});


	}

	private void Actions() {

		Login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

              /*  Intent i = new Intent(getContext(), MainActivity.class);
                Utils.User_Login=true;
                i.putExtra("User_Login", true);
                startActivity(i);
                getActivity().finish();*/


				if (mViewModel.Check(phone.getText().toString(), Password.getText().toString())) {
					StartProgress();
					mViewModel.Login(signActivity, phone.getText().toString(), Password.getText().toString());

				}
			}
		});
//
//		SignUp.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				goToFragment(new SignUpFragment());
//			}
//		});
		Skip_Registration.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getContext(), MainActivity.class);
				startActivity(i);
				getActivity().finish();

				//goToFragment(new HomeFragment());

			}
		});
	}

	private void Definations(View root) {
		Login = root.findViewById(R.id.Sign_in);
		Password = root.findViewById(R.id.et_password);
		phone = root.findViewById(R.id.phone);
		Layout = root.findViewById(R.id.Layout);
		Progrees = root.findViewById(R.id.Progress);



	}

	private void goToFragment(Fragment fragment) {
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.container, fragment);
		ft.addToBackStack(null);
		ft.commit();
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