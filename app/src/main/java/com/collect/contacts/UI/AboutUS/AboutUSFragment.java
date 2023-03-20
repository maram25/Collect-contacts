package com.collect.contacts.UI.AboutUS;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collect.contacts.Models.StaticPagesModel;
import com.collect.contacts.R;

import java.util.List;

public class AboutUSFragment extends Fragment {

	private AboutUViewModel mViewModel;

	TextView tv;
	TextView egy;

	ConstraintLayout Layout;
	RelativeLayout Progrees;

	public static AboutUSFragment newInstance() {
		return new AboutUSFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.about_u_fragment, container, false);

		mViewModel.GetAboutUs();


		if (container != null) {
			container.removeAllViews();
		}


		Definations(root);
		Actions();
		Observers();
		SetUP();


		return root;
	}

	private void SetUP() {
		egy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://egydesigner.com"));
				startActivity(i);
			}
		});
	}

	private void Observers() {

		mViewModel.aboutus.observe(getViewLifecycleOwner(), new Observer<List<StaticPagesModel.Data.pages>>() {
			@Override
			public void onChanged(List<StaticPagesModel.Data.pages> pages) {
				tv.setText(pages.get(0).getTitle());

			}
		});

	}

	private void Actions() {
		tv.setMovementMethod(new ScrollingMovementMethod());
	}

	private void Definations(View root) {

		egy = root.findViewById(R.id.egy);

		tv = root.findViewById(R.id.text_details);
		Layout = root.findViewById(R.id.Layout);
		Progrees = root.findViewById(R.id.Progress);
	}

}

