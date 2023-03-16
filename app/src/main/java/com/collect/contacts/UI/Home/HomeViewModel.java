package com.collect.contacts.UI.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.LoginModel;
import com.collect.contacts.Models.UserProfileModel;
import com.collect.contacts.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
	// TODO: Implement the ViewModel

	APIInterface apiInterface;
	MutableLiveData<Boolean> LOGOUT = new MutableLiveData<>();
	MutableLiveData<Boolean> tryAgain = new MutableLiveData<>();
	MutableLiveData<UserProfileModel.Data.User> User = new MutableLiveData<>();
	public void UserProfile() {
		apiInterface = APIClient.getClient().create(APIInterface.class);
		Call<UserProfileModel> call = apiInterface.UserProfile(Utils.Token);
		 call.enqueue(new Callback<UserProfileModel>() {
			 @Override
			 public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
				 UserProfileModel resource = response.body();

				 if (response.isSuccessful()) {
					 User.setValue(resource.getData().getUser());
				 }
				 else if (response.code() == 400){
					 tryAgain.setValue(true);
				 }
 else if (response.code() == 500){
					 tryAgain.setValue(true);
				 }


			 }

			 @Override
			 public void onFailure(Call<UserProfileModel> call, Throwable t) {

			 }
		 });

	}
	public void lOGOUT() {
		apiInterface = APIClient.getClient().create(APIInterface.class);
		Call<LoginModel> call = apiInterface.logout(Utils.Token);
		call.enqueue(new Callback<LoginModel>() {
			@Override
			public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
				if (response.isSuccessful()) {
					LOGOUT.setValue(true);
				} else
					LOGOUT.setValue(true);

			}

			@Override
			public void onFailure(Call<LoginModel> call, Throwable t) {

			}
		});
	}
}