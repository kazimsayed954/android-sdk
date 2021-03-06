package com.loginradius.androidsdk.response.page;

import com.google.gson.annotations.SerializedName;

public class LoginRadiusPageLocations 
{
	@SerializedName("Street")
	public String Street ;
	@SerializedName("City")
    public String City ;
	@SerializedName("State")
    public String State ;
	@SerializedName("Country")
    public LoginRadiusCountryCodeName Country ;
	@SerializedName("Zip")
    public String Zip ;
	@SerializedName("Latitude")
    public double Latitude;
	@SerializedName("Longitude")
    public double Longitude ;
	@SerializedName("Phone")
    public String Phone;
}
