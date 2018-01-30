package jimmy.example.com.dsgapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VenuesItem{

	@SerializedName("ratingSignals")
	private int ratingSignals;

	@SerializedName("name")
	private String name;

	@SerializedName("verified")
	private boolean verified;

	@SerializedName("ratingColor")
	private String ratingColor;

	@SerializedName("rating")
	private double rating;

	@SerializedName("location")
	private Location location;

	@SerializedName("id")
	private String id;

	@SerializedName("storeId")
	private String storeId;

	@SerializedName("photos")
	private List<PhotosItem> photos;

	@SerializedName("url")
	private String url;

	@SerializedName("contacts")
	private List<ContactsItem> contacts;

	public void setRatingSignals(int ratingSignals){
		this.ratingSignals = ratingSignals;
	}

	public int getRatingSignals(){
		return ratingSignals;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setVerified(boolean verified){
		this.verified = verified;
	}

	public boolean isVerified(){
		return verified;
	}

	public void setRatingColor(String ratingColor){
		this.ratingColor = ratingColor;
	}

	public String getRatingColor(){
		return ratingColor;
	}

	public void setRating(double rating){
		this.rating = rating;
	}

	public double getRating(){
		return rating;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStoreId(String storeId){
		this.storeId = storeId;
	}

	public String getStoreId(){
		return storeId;
	}

	public void setPhotos(List<PhotosItem> photos){
		this.photos = photos;
	}

	public List<PhotosItem> getPhotos(){
		return photos;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setContacts(List<ContactsItem> contacts){
		this.contacts = contacts;
	}

	public List<ContactsItem> getContacts(){
		return contacts;
	}

	@Override
 	public String toString(){
		return 
			"VenuesItem{" + 
			"ratingSignals = '" + ratingSignals + '\'' + 
			",name = '" + name + '\'' + 
			",verified = '" + verified + '\'' + 
			",ratingColor = '" + ratingColor + '\'' + 
			",rating = '" + rating + '\'' + 
			",location = '" + location + '\'' + 
			",id = '" + id + '\'' + 
			",storeId = '" + storeId + '\'' + 
			",photos = '" + photos + '\'' + 
			",url = '" + url + '\'' + 
			",contacts = '" + contacts + '\'' + 
			"}";
		}
}