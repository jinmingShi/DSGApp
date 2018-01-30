package jimmy.example.com.dsgapp.model;


import com.google.gson.annotations.SerializedName;

public class ContactsItem{

	@SerializedName("twitter")
	private String twitter;

	@SerializedName("phone")
	private String phone;

	@SerializedName("facebook")
	private String facebook;

	@SerializedName("facebookName")
	private String facebookName;

	public void setTwitter(String twitter){
		this.twitter = twitter;
	}

	public String getTwitter(){
		return twitter;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setFacebook(String facebook){
		this.facebook = facebook;
	}

	public String getFacebook(){
		return facebook;
	}

	public void setFacebookName(String facebookName){
		this.facebookName = facebookName;
	}

	public String getFacebookName(){
		return facebookName;
	}

	@Override
 	public String toString(){
		return 
			"ContactsItem{" + 
			"twitter = '" + twitter + '\'' + 
			",phone = '" + phone + '\'' + 
			",facebook = '" + facebook + '\'' + 
			",facebookName = '" + facebookName + '\'' + 
			"}";
		}
}