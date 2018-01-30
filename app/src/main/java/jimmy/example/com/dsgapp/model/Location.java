package jimmy.example.com.dsgapp.model;

import com.google.gson.annotations.SerializedName;

public class Location{

	@SerializedName("cc")
	private String cc;

	@SerializedName("country")
	private String country;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("postalCode")
	private String postalCode;

	@SerializedName("state")
	private String state;

	@SerializedName("longitude")
	private double longitude;

	public void setCc(String cc){
		this.cc = cc;
	}

	public String getCc(){
		return cc;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"cc = '" + cc + '\'' + 
			",country = '" + country + '\'' + 
			",address = '" + address + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",postalCode = '" + postalCode + '\'' + 
			",state = '" + state + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}