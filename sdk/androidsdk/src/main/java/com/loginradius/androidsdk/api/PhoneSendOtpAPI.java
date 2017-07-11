package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;
import com.loginradius.androidsdk.response.phonesendotp.PhoneSendOtpData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


public class PhoneSendOtpAPI {
    public void getResponse(LoginParams value, final AsyncHandler<PhoneSendOtpData> handler) {
        String smstemplate = (value.getSmsTemplate()!=null) ? value.getSmsTemplate() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", value.getApikey());
        params.put("phone", value.getPhone());
        params.put("smstemplate",smstemplate);


        ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);
        apiService.getphonesendOtp(Endpoint.API_V2_PHONESENDOTPAPI,params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PhoneSendOtpData>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }

                    @Override
                    public void onNext(PhoneSendOtpData response) {
                        handler.onSuccess(response);
                    }

                });
    }
}
