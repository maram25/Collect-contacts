package com.collect.contacts.UI.Groups;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Models.GroupsModel;
import com.collect.contacts.Models.UserProfileModel;
import com.collect.contacts.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsViewModel extends ViewModel {
	// TODO: Implement the ViewModel


	APIInterface apiInterface;
	MutableLiveData<List<GroupsModel.Data.Groups>> Groups = new MutableLiveData<List<GroupsModel.Data.Groups>>();


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