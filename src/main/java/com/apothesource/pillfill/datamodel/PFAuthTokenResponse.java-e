/* 
 * The MIT License
 *
 * Copyright 2015 Apothesource, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.apothesource.pillfill.datamodel;

import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by rammic on 12/8/14.
 */
public class PFAuthTokenResponse implements Serializable {
    public static Gson gson = new GsonBuilder().registerTypeAdapter(PFAuthTokenResponse.class, new AuthTokenDeserializer()).disableHtmlEscaping().create();
    public boolean success = false;
    public String message;
    public int errorCode;
    public AuthToken authToken;
    public transient Throwable exception;

    public static PFAuthTokenResponse parseAuthTokenResponse(String token) {
        PFAuthTokenResponse response = gson.fromJson(token, PFAuthTokenResponse.class);
        return response;
    }

    private static class AuthTokenDeserializer implements JsonDeserializer<PFAuthTokenResponse> {
        private static Gson gson = new GsonBuilder().setDateFormat("MMddyyyyhhmmssZ").disableHtmlEscaping().create();

        @Override
        public PFAuthTokenResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PFAuthTokenResponse response = gson.fromJson(json, PFAuthTokenResponse.class);
            if (response.authToken != null) {
                JsonObject token = json.getAsJsonObject().get("authToken").getAsJsonObject();
                response.authToken.setRawToken(gson.toJson(token));
            }
            return response;
        }
    }
}
