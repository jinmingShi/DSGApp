package jimmy.example.com.dsgapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response{

	@SerializedName("venues")
	private List<VenuesItem> venues;

	public void setVenues(List<VenuesItem> venues){
		this.venues = venues;
	}

	public List<VenuesItem> getVenues(){
		return venues;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"venues = '" + venues + '\'' + 
			"}";
		}
}