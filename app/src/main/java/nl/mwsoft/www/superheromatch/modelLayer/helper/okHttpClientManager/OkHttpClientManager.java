/*
  Copyright (C) 2019 - 2021 MWSOFT
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import nl.mwsoft.www.superheromatch.MyApplication;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpClientManager {

    public static OkHttpClient setUpSecureClient(Context context) {

        try {
            // Load CAs from a resource
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream ins = MyApplication.context().getResources().openRawResource(
                    MyApplication.context().getResources().getIdentifier("certificate",
                            "raw", MyApplication.context().getPackageName()));

            Certificate ca;

            try (InputStream caInput = new BufferedInputStream(ins)) {
                ca = cf.generateCertificate(caInput);
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Instances of this class represent a secure socket protocol implementation which acts
            // as a factory for secure socket factories or SSLEngines. This class is initialized with
            // an optional set of key and trust managers and source of secure random bytes.
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(),
                    new java.security.SecureRandom());

            // SSL (Secure Sockets Layer) is the standard security technology for establishing an
            // encrypted link between a web server and a browser. This link ensures that all data
            // passed between the web server and browsers remain private and integral.

            // Socket is full-duplex, which means you can send and receive data simultaneously

            // Create an ssl socket factory
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            SharedPreferences prefs = context.getSharedPreferences(
                    ConstantRegistry.SHARED_PREFERENCES,
                    Context.MODE_PRIVATE
            );

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader(
                                                    "Authorization",
                                                    "Bearer " + prefs.getString(ConstantRegistry.ACCESS_TOKEN, "")
                                            ).build();
                                    return chain.proceed(request);
                                }
                            })
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkHttpClient setUpSecureClientWithoutAuthorization() {

        try {
            // Load CAs from a resource
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream ins = MyApplication.context().getResources().openRawResource(
                    MyApplication.context().getResources().getIdentifier("certificate",
                            "raw", MyApplication.context().getPackageName()));

            Certificate ca;

            try (InputStream caInput = new BufferedInputStream(ins)) {
                ca = cf.generateCertificate(caInput);
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Instances of this class represent a secure socket protocol implementation which acts
            // as a factory for secure socket factories or SSLEngines. This class is initialized with
            // an optional set of key and trust managers and source of secure random bytes.
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(),
                    new java.security.SecureRandom());

            // SSL (Secure Sockets Layer) is the standard security technology for establishing an
            // encrypted link between a web server and a browser. This link ensures that all data
            // passed between the web server and browsers remain private and integral.

            // Socket is full-duplex, which means you can send and receive data simultaneously

            // Create an ssl socket factory
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
