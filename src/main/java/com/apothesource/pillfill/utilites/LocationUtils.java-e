/*
 *
 *  * The MIT License
 *  *
 *  * Copyright {$YEAR} Apothesource, Inc.
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */

package com.apothesource.pillfill.utilites;

import com.apothesource.pillfill.datamodel.aggregation.Point;
import com.apothesource.pillfill.datamodel.google.GoogleGeocode;
import com.apothesource.pillfill.datamodel.google.GoogleGeocode.GeocodeResponse;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import rx.Observable;
import timber.log.Timber;

import java.io.IOException;
import java.net.URLEncoder;

public class LocationUtils {
    public static final String GOOGLE_GEOCODE_SVC_TEMPLATE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=true";
    public static Gson gson = new Gson();

    public static Observable<Point> getLocationForAddress(String address){
        return ReactiveUtils.subscribeIoObserveImmediate(subscriber -> {
            String url = String.format(GOOGLE_GEOCODE_SVC_TEMPLATE_URL,
                    URLEncoder.encode(address));
            OkHttpClient client = PFNetworkManager.getStandardHttpClient();
            Request geocodeRequest = new Request.Builder().url(url).build();
            try {
                Response geocodeResponse = client.newCall(geocodeRequest).execute();
                GeocodeResponse response = gson.fromJson(geocodeResponse.body().charStream(), GeocodeResponse.class);
                if(!response.getResults().isEmpty()) {
                    GoogleGeocode.Location location = response.getResults().get(0).getGeometry().getLocation();
                    Point p = new Point(location.getLat(),location.getLng());
                    subscriber.onNext(p);
                }
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e(e, "Couldn't geocode zip.");
                subscriber.onError(e);
            }

        });
    }
}
