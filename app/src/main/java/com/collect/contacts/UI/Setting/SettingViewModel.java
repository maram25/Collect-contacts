package com.collect.contacts.UI.Setting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.LoginModel;
import com.collect.contacts.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingViewModel extends ViewModel {
	// TODO: Implement the ViewModel


	APIInterface apiInterface;
	MutableLiveData<Boolean> LOGOUT = new MutableLiveData<>();
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