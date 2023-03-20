package com.collect.contacts.UI.AboutUS;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.collect.contacts.Calls.APIClient;
import com.collect.contacts.Calls.APIInterface;
import com.collect.contacts.Models.StaticPagesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUViewModel extends ViewModel {
	APIInterface apiInterface;

	MutableLiveData<List<StaticPagesModel.Data.pages>> aboutus= new MutableLiveData<List<StaticPagesModel.Data.pages>>();
	MutableLiveData<Boolean> UnAuth=new MutableLiveData<>();




	public void GetAboutUs()
	{
		apiInterface = APIClient.getClient().create(APIInterface.class);

		Call<StaticPagesModel> call = apiInterface.GetInfos();
		call.enqueue(new Callback<StaticPagesModel>() {
			@Override
			public void onResponse(Call<StaticPagesModel> call, Response<StaticPagesModel> response) {

				StaticPagesModel resource=response.body();
				if (response.isSuccessful()) {
					aboutus.setValue(resource.getData().getPages());

				}else  if ( response.code() == 422 )
					UnAuth.setValue(true);

			}
			@Override
			public void onFailure(Call<StaticPagesModel> call, Throwable t) {

			}
		});

	}
}