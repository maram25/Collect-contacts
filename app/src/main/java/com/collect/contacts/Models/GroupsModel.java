package com.collect.contacts.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GroupsModel {

	@SerializedName("data")
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		@SerializedName("groups")
		List<Groups> groups = new ArrayList<>();

		public List<Groups> getGroups() {
			return groups;
		}

		public void setGroups(List<Groups> groups) {
			this.groups = groups;
		}

		public class Groups {
			@SerializedName("name")
			private String name;
			@SerializedName("count_phones")
			private String count_phones;

			@SerializedName("file_url")
			private String file_url;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getCount_phones() {
				return count_phones;
			}

			public void setCount_phones(String count_phones) {
				this.count_phones = count_phones;
			}

			public String getFile_url() {
				return file_url;
			}

			public void setFile_url(String file_url) {
				this.file_url = file_url;
			}

			public List<String> getPhones() {
				return phones;
			}

			public void setPhones(List<String> phones) {
				this.phones = phones;
			}

			@SerializedName("phones")
			List<String> phones = new ArrayList<>();

		}


	}
}
