package com.collect.contacts.UI.Login;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.LoginModel;
import com.collect.contacts.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends ViewModel {


	MutableLiveData<String> NoInternet=new MutableLiveData<>();
	MutableLiveData<Boolean> PhoneError=new MutableLiveData<>();
	MutableLiveData<Boolean> PasswordError=new MutableLiveData<>();
	MutableLiveData<Boolean> verifiedError=new MutableLiveData<>();
	MutableLiveData<Boolean> EmailError=new MutableLiveData<>();
	MutableLiveData<Boolean> EmailNotValid=new MutableLiveData<>();
	MutableLiveData<Boolean> LoginError=new MutableLiveData<>();
	MutableLiveData<Boolean> Login=new MutableLiveData<>();
	MutableLiveData<Boolean> UnAuth = new MutableLiveData<>();
	MutableLiveData<Boolean> SomeThingWrong = new MutableLiveData<>();

	Context mcontext ;


	public SharedPreferences sp;
	public  SharedPreferences.Editor Ed;
	boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	public boolean Check(String Email,String Password) {
//		if (Phone.equals(""))
//		{
//			PhoneError.setValue(true);
//			return false;
//		}
		if (Email.equals("")){
			EmailError.setValue(true);
			return false;
		}
		if (!isEmailValid(Email)) {
			EmailNotValid.setValue(true);
			return false;
		}

		if (Password.equals(""))
		{
			PasswordError.setValue(true);
			return false;
		}

		return true;
	}
	public void Login(Activity activity, String email, String Password)     {
		APIInterface apiInterface;
		apiInterface = APIClient.getClient().create(APIInterface.class);
		Call<LoginModel> call = apiInterface.Login(Utils.Lang,email,Password);
		 call.enqueue(new Callback<LoginModel>() {
			 @Override
			 public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
				 LoginModel resource=response.body();
				 if (response.isSuccessful()) {
					 sp=activity.getSharedPreferences("Login", MODE_PRIVATE);
					 SharedPreferences sp1=activity.getSharedPreferences("Login", MODE_PRIVATE);
					 Ed = sp.edit();

					 Ed.putBoolean("User_Login", true);
					 Ed.putString("E_mail", resource.getData().getUser().getOriginal().getEmail());
					 Ed.putString("Psw", Password);
					 Ed.putString("Name", resource.getData().getUser().getOriginal().getName());
					 Ed.putString("balance", resource.getData().getUser().getOriginal().getBalance());


					 Utils.Token = resource.getData().getUser().getOriginal().getAccess_token();
					 Utils.Name=resource.getData().getUser().getOriginal().getName();
					 Utils.Phone=resource.getData().getUser().getOriginal().getPhone();
					 Utils.Email=resource.getData().getUser().getOriginal().getEmail();

					 Ed.putString("Token",resource.getData().getUser().getOriginal().getAccess_token());
					 Ed.putString("E_mail",resource.getData().getUser().getOriginal().getEmail());
					 //  Ed.putString("Name",resource.getName());

					 Ed.commit();
					 Log.e("token" ,resource.getData().getUser().getOriginal().getAccess_token());

					 Login.setValue(true);
				 }
				 else if ( response.code() == 422 ){
					 verifiedError.setValue(true);
				 }
				 else {
					 LoginError.setValue(true);
					 try {
						 JSONObject jobError = new JSONObject(response.errorBody().string());
						 Log.d("error", jobError.getString("message"));
						 Log.d("error", "1");


					 } catch (JSONException e) {
						 e.printStackTrace();
						 Log.d("error", "2");
					 } catch (IOException e) {
						 Log.d("error", "3");
						 e.printStackTrace();
					 }
				 }

			 }

			 @Override
			 public void onFailure(Call<LoginModel> call, Throwable t) {
				 NoInternet.setValue(t.getMessage());

			 }
		 });
	}
}