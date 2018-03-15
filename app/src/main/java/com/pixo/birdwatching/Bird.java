package com.pixo.birdwatching;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Bird implements Serializable {

	@SerializedName("Id")
	private long id;
	@SerializedName("NameEnglish")
	private String nameEnglish;

	public Bird() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	@Override
	public String toString() {
		return nameEnglish + id;
	}
}