package com.collect.contacts.UI.Groups;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Models.GroupsModel;
import com.collect.contacts.Models.UserProfileModel;
import com.collect.contacts.Utils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsViewModel extends ViewModel {
	// TODO: Implement the ViewModel


	APIInterface apiInterface;
	MutableLiveData<List<GroupsModel.Data.Groups>> Groups = new MutableLiveData<List<GroupsModel.Data.Groups>>();

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

	public void GetGroups() {
		apiInterface = APIClient.getClient().create(APIInterface.class);
		Call<GroupsModel> call = apiInterface.GetGroups(Utils.Token);
		 call.enqueue(new Callback<GroupsModel>() {
			 @Override
			 public void onResponse(Call<GroupsModel> call, Response<GroupsModel> response) {
				 GroupsModel resource = response.body();
				 if (response.isSuccessful()) {
					 Groups.setValue(resource.getData().getGroups());
				 }
			 }

			 @Override
			 public void onFailure(Call<GroupsModel> call, Throwable t) {

			 }
		 });

	}
}