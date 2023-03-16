package com.collect.contacts.UI.GroupsStore;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Models.GroupsModel;
import com.collect.contacts.Utils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsStoreViewModel extends ViewModel {


	APIInterface apiInterface;
	MutableLiveData<Boolean> GroupStore = new MutableLiveData<>();
	MutableLiveData<Boolean> tryAgain = new MutableLiveData<>();
	public void GroupS(String name , List<String> phones) {
		HashMap<String, Object> PaymentKey = new HashMap<String, Object>();
		apiInterface = APIClient.getClient().create(APIInterface.class);
		ContactModel contactModel = new ContactModel();

		Call<GroupsModel> call = apiInterface.GroupStore(Utils.Token,name,phones);
		call.enqueue(new Callback<GroupsModel>() {
			@Override
			public void onResponse(Call<GroupsModel> call, Response<GroupsModel> response) {
				GroupsModel resource = response.body();
				if (response.isSuccessful()) {
					GroupStore.setValue(true);
				}
				else if (response.code() == 400){
					tryAgain.setValue(true);
				}
			}

			@Override
			public void onFailure(Call<GroupsModel> call, Throwable t) {

			}
		});
	}



}