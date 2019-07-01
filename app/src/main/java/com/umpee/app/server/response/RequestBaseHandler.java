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

import android.text.TextUtils;

import com.umpee.app.cache.Cache;
import com.umpee.app.model.ModelVideo;
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

public class RequestBaseHandler implements RequestHandler {
    private static final String TOKEN_PREFIX = "umpee_user_";

    @SuppressWarnings("deprecation")
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
    }

    String failedResponse(String reason) {
        return "{" +
//                "\"success\":" + "false" + "," +
                "\"message\":" + "\"" + reason + "\"" +
                "}";

    }

    @SuppressWarnings("deprecation")
    boolean checkAuth(HttpRequest request) throws HttpException, IOException{
        Map<String, String> params = HttpRequestParser.parse(request);
        String token = params.get("token");
        if (TextUtils.isEmpty(token)) return false;
        token = URLDecoder.decode(token, "utf-8");
        return token.startsWith(TOKEN_PREFIX);
    }


}
