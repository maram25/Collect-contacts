package com.collect.contacts.UI.ContactList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Utils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListViewModel extends ViewModel {
	APIInterface apiInterface;
	MutableLiveData<ContactModel> ContactList = new MutableLiveData<>();

	public void SendContact( String sender_id ,List<String> phones,String msg) {
		HashMap<String, Object> PaymentKey = new HashMap<String, Object>();
		apiInterface = APIClient.getClient().create(APIInterface.class);
		ContactModel contactModel = new ContactModel();

		Call<ContactModel> call = apiInterface.SendSMS(Utils.Token,sender_id,phones,msg);
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