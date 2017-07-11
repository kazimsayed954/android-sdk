package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.photo.LoginRadiusPhoto;

import java.io.IOException;
import java.util.Arrays;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
The photo API is used to get photo data from the user's social account. 
The data will be normalized into LoginRadius data format
*/
public class PhotoAPI 
{

	private static final String[] providers = {"facebook", "msn", "renren", "vk", "google"};
    private String albumId;
	public String getAlbumId() {return albumId;}
	public void setAlbumId(String albumId) {this.albumId = albumId;}
	
	/**
	 * Gives user's photos on social providers
	 * @param token token Authentication token from LoginRadius
	 * @param handler Used to handle the success and failure events
	 */
	public void getResponse(lrAccessToken token,final AsyncHandler<LoginRadiusPhoto[]> handler)
	{
		if (!Arrays.asList(providers).contains(token.provider.toLowerCase())) {
			handler.onFailure(new Throwable(), "lr_API_NOT_SUPPORTED");
			return;
		}
		ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
		apiService.getPhoto(Endpoint.API_V2_PHOTO,token.access_token,albumId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new DisposableObserver<LoginRadiusPhoto[]>() {
					@Override
					public void onComplete() {}

					@Override
					public void onError(Throwable e) {
						ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
						handler.onFailure(exceptionResponse.t, exceptionResponse.message);
					}

					@Override
					public void onNext(LoginRadiusPhoto[] response) {
						handler.onSuccess(response);
					}

				});
	}
}
