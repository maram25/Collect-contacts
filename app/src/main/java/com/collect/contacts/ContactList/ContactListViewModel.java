package com.collect.contacts.ContactList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.ContactModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListViewModel extends ViewModel {
	APIInterface apiInterface;
	MutableLiveData<ContactModel> ContactList = new MutableLiveData<>();

	public void SendContact() {
		HashMap<String, Object> PaymentKey = new HashMap<String, Object>();
		apiInterface = APIClient.getClient().create(APIInterface.class);
		ContactModel contactModel = new ContactModel();

		Call<ContactModel> call = apiInterface.ContactList(PaymentKey);
		call.enqueue(new Callback<ContactModel>() {
			@Override
			public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
				ContactModel resource = response.body();
				if (response.isSuccessful()) {
					ContactList.setValue(resource);
				}
				else if (response.code() == 400){

				}
			}

			@Override
			public void onFailure(Call<ContactModel> call, Throwable t) {

			}
		});
	}


}