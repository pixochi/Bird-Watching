package com.pixo.birdwatching;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Observation implements Serializable {

	@SerializedName("BirdId")
	private long birdId;
	@SerializedName("Created")
	private String created;
	@SerializedName("Id")
	private long id;
	@SerializedName("Latitude")
	private float latitude;
	@SerializedName("Longitude")
	private float longitude;
	@SerializedName("NameDanish")
	private String nameDanish;
	@SerializedName("NameEnglish")
	private String nameEnglish;
	@SerializedName("Placename")
	private String placename;
	@SerializedName("Population")
	private int population;
	@SerializedName("UserId")
	private String userId;

	public Observation() {
	}

	public Observation(long birdId, float latitude, float longitude, String nameDanish, String nameEnglish, String userId) {
		this.birdId = birdId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.nameDanish = nameDanish;
		this.nameEnglish = nameEnglish;
		this.userId = userId;
	}

	public long getBirdId() {
		return birdId;
	}

	public void setBirdId(long birdId) {
		this.birdId = birdId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getNameDanish() {
		return nameDanish;
	}

	public void setNameDanish(String nameDanish) {
		this.nameDanish = nameDanish;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	public String getPlacename() {
		return placename;
	}

	public void setPlacename(String placename) {
		this.placename = placename;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
