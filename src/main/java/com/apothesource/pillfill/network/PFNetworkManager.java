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
package com.apothesource.pillfill.network;

import com.apothesource.pillfill.utilites.ResourceUtil;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.security.GeneralSecurityException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import timber.log.Timber;

public class PFNetworkManager {

    public static final int DEFAULT_CACHE_SIZE = 100 * 1024 * 1024; //100MB
    private static OkHttpClient pfPinnedClient;
    private static OkHttpClient standardClient;
    private static boolean isHttpClientInit = false;

    public static OkHttpClient getPinnedPFHttpClient(boolean allowWeakCiphers) {
        if(!isHttpClientInit){
            initHttpClient(allowWeakCiphers);
        }
        return pfPinnedClient;
    }
    public static OkHttpClient getStandardHttpClient() {
        if(!isHttpClientInit){
            initHttpClient(false);
        }
        return standardClient;
    }

    private static Cache getDefaultCache(){
        try {
            return new Cache(Files.createTempDirectory("networkCache").toFile(),DEFAULT_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OkHttpClient getPinnedPFHttpClient() {
        return getPinnedPFHttpClient(false);
    }

    public static String doPinnedGetForUrl(String url) throws IOException {
        OkHttpClient client = getPinnedPFHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Response response = client.newCall(requestBuilder.build()).execute();
        return response.body().string();
    }
    public static String doPinnedGetForUrl(String url, String authToken) throws IOException {
        OkHttpClient client = getPinnedPFHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if(authToken != null) requestBuilder = requestBuilder.addHeader("Bearer", authToken);
        Response response = client.newCall(requestBuilder.build()).execute();
        return response.body().string();
    }
    
    public static HttpsURLConnection getPFHttpsURLConnection(String url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setSSLSocketFactory(new PFSSLSocketFactory());
        return connection;
    }
    private static synchronized void initHttpClient(boolean allowWeakCiphers){
        if(pfPinnedClient == null) {
            pfPinnedClient = new OkHttpClient();
            String apiKey = ResourceUtil.getInstance().getPFApiKey();
            if(apiKey == null){
                throw new RuntimeException("API_KEY is not set.");
            }
            pfPinnedClient.setSslSocketFactory(new PFSSLSocketFactory(allowWeakCiphers));
            pfPinnedClient.setHostnameVerifier(PFSSLSocketFactory.getPfHostnameVerifier());
            pfPinnedClient.networkInterceptors().add(new ApiKeyInterceptor(apiKey));
            pfPinnedClient.setCache(getDefaultCache());
        }
        if(standardClient == null){
            standardClient = new OkHttpClient();
            pfPinnedClient.setCache(getDefaultCache());
        }
        isHttpClientInit = true;
    }

}
class ApiKeyInterceptor implements Interceptor {

    private final String apiKey;

    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request requestWithApiKey = request.newBuilder()
            .addHeader("api_key", apiKey)
            .build();
        return chain.proceed(requestWithApiKey);
    }
}
class PFSSLSocketFactory extends SSLSocketFactory {
    private static final String[] SSL_ENABLED_PROTOCOLS = new String[]{"TLSv1.2", "TLSv1.1"};

    private static final String[] SUPPORTED_CIPHER_SUITES = new String[]{
            "TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", "TLS_ECDH_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA"};
    private static final String[] PREFERRED_CIPHER_SUITES = new String[]{
            "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA"};

    private static final String PF_HOSTNAME_REGEX= ".*\\.pill[-]?fill.com";

    private static SSLContext tlsContext;

    private static HostnameVerifier pfHostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String host, SSLSession session) {
            return checkHost(host);
        }
        private boolean checkHost(String host) {
            if (!host.matches(PF_HOSTNAME_REGEX)) {
                Timber.w("Rejecting access to non-PF url: %s", host);
                return false;
            }
            return true;
        }
    };

    public PFSSLSocketFactory(boolean allowWeakCiphers){
        super();
        initSSLContext(allowWeakCiphers);
    }

    public PFSSLSocketFactory() {
        this(false);
    }

    public static HostnameVerifier getPfHostnameVerifier(){
        return pfHostnameVerifier;
    }


    private static void initSSLContext() {
        initSSLContext(false);
    }

    private static void initSSLContext(boolean allowWeakCiphers) {
        try {
            if (tlsContext == null) {
                PillFillTrustManager pftm = new PillFillTrustManager(allowWeakCiphers);
                tlsContext = SSLContext.getInstance("TLS");
                tlsContext.init(null, new TrustManager[]{pftm}, null);
            }
        } catch (GeneralSecurityException e) {
            Timber.e(e,"Error initializing SSLContext.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return PREFERRED_CIPHER_SUITES;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return SUPPORTED_CIPHER_SUITES;
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port,
                               boolean autoClose) throws IOException {
        return tlsContext.getSocketFactory().createSocket(socket, host, port,
                autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        SSLSocket sslSocket = (SSLSocket) tlsContext.getSocketFactory().createSocket();
        sslSocket.setEnabledProtocols(SSL_ENABLED_PROTOCOLS);
        return sslSocket;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        SSLSocket sslSocket = (SSLSocket) tlsContext.getSocketFactory().createSocket(host, port);
        sslSocket.setEnabledProtocols(SSL_ENABLED_PROTOCOLS);
        return sslSocket;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        SSLSocket sslSocket = (SSLSocket) tlsContext.getSocketFactory().createSocket(host, port, localHost, localPort);
        sslSocket.setEnabledProtocols(SSL_ENABLED_PROTOCOLS);
        return sslSocket;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        SSLSocket sslSocket = (SSLSocket) tlsContext.getSocketFactory().createSocket(host, port);
        sslSocket.setEnabledProtocols(SSL_ENABLED_PROTOCOLS);
        return sslSocket;
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        SSLSocket sslSocket = (SSLSocket) tlsContext.getSocketFactory().createSocket(address, port, localAddress, localPort);
        sslSocket.setEnabledProtocols(SSL_ENABLED_PROTOCOLS);
        return sslSocket;
    }
}