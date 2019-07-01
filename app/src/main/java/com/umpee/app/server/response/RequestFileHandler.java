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

import android.os.Environment;

import com.umpee.app.cache.Cache;
import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * <p>Returns a file.</p>
 * Created by Yan Zhenjie on 2016/7/1.
 */
public class RequestFileHandler extends RequestBaseHandler {

    @SuppressWarnings("deprecation")
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        StringEntity stringEntity;
        if (Cache.getVideo().checkVideo()) {
            response.setStatusCode(200);
            File file = new File(Cache.getVideo().url);
            long contentLength = file.length();
            response.setHeader("Accept-Ranges", "bytes");
//            response.setHeader("ContentLength", Long.toString(contentLength));
            response.setEntity(new FileEntity(file, HttpRequestParser.getMimeType(file.getName())));
        } else {
            response.setStatusCode(404);
            stringEntity = new StringEntity(failedResponse("No video recorded"), "utf-8");
            response.setEntity(stringEntity);
        }
    }
}
