package com.timsterj.data.common;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.helpers.Tls12SocketFactory;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseURL) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor);

        bindSSL19API(client);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Contracts.RetrofitConstant.DEMO_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client.build())
                    .build();
        }

        return retrofit;
    }

    public static void bindSSL19API(OkHttpClient.Builder client) {
        X509TrustManager trustManager = null;

        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException();
            }
            trustManager = (X509TrustManager) trustManagers[0];

        } catch (KeyStoreException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, new TrustManager[]{trustManager}, null);
            client.sslSocketFactory(new Tls12SocketFactory(
                    sc.getSocketFactory()
            ), trustManager);
            ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build();

            client.connectionSpecs(Collections.singletonList(cs));

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }


    }

}
