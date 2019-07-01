package com.umpee.app.network;


import com.umpee.app.MyApp;
import com.umpee.app.R;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


public class NetError {
    private String message;
    private int code;

    public NetError() {
    }

    public static NetError parseError(Response<?> response) {
        Converter<ResponseBody, NetError> converter =
                NetApi.getClient().responseBodyConverter(NetError.class, new Annotation[0]);

        NetError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new NetError();
        }

        return error;
    }

    public String getMessage() {
        if (message == null) {
            message = MyApp.getContext().getString(R.string.error_unknown_issue);
        }
        return message;
    }

    public int getCode() {
        return code;
    }
}
