package jimmy.example.com.dsgapp.model;

import com.google.gson.annotations.SerializedName;

public class PhotosItem{

	@SerializedName("createdAt")
	private int createdAt;

	@SerializedName("photoId")
	private String photoId;

	@SerializedName("url")
	private String url;

	public void setCreatedAt(int createdAt){
		this.createdAt = createdAt;
	}

	public int getCreatedAt(){
		return createdAt;
	}

	public void setPhotoId(String photoId){
		this.photoId = photoId;
	}

	public String getPhotoId(){
		return photoId;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"PhotosItem{" + 
			"createdAt = '" + createdAt + '\'' + 
			",photoId = '" + photoId + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}