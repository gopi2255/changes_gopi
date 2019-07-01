/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.umpee.app.server.response;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.umpee.app.MyApp;
import com.umpee.app.utils.DialogUtils;
import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

public class RequestOutHandler implements RequestHandler {
    private static final String TOKEN_PREFIX = "umpee_user_";

    @SuppressWarnings("deprecation")
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        StringEntity stringEntity;
        if (!checkAuth(request)) {
            response.setStatusCode(404);
            stringEntity = new StringEntity(failedResponse("Invalid user"), "utf-8");
            response.setEntity(stringEntity);
            return;
        }
        Map<String, String> params = HttpRequestParser.parse(request);
        String isOut = params.get("isOut");
        if (TextUtils.isEmpty(isOut)) {
            response.setStatusCode(404);
            stringEntity = new StringEntity(failedResponse("Success"), "utf-8");
            response.setEntity(stringEntity);
            return;
        }

        final String res = "1".equals(isOut) ? "OUT." : "NOT OUT.";
        Log.i("out", "handle: " + res);
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                DialogUtils.showOkayDialog(MyApp.getContext(), "Message from 3rd umpire", "Result is " + res, "OK", new DialogUtils.OnOkayCancelListener() {
//                    @Override
//                    public void onOkay() {
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//            }
//        });
        response.setStatusCode(200);
        stringEntity = new StringEntity(failedResponse("Success"), "utf-8");
        response.setEntity(stringEntity);

    }

    String failedResponse(String reason) {
        return "{" +
                "\"message\":" + "\"" + reason + "\"" +
                "}";

    }

    @SuppressWarnings("deprecation")
    boolean checkAuth(HttpRequest request) throws HttpException, IOException {
        Map<String, String> params = HttpRequestParser.parse(request);
        String token = params.get("token");
        if (TextUtils.isEmpty(token)) return false;
        token = URLDecoder.decode(token, "utf-8");
        return token.startsWith(TOKEN_PREFIX);
    }


}
