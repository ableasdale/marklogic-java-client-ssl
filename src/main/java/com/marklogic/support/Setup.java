package com.marklogic.support;

import com.burgstaller.okhttp.AuthenticationCacheInterceptor;
import com.burgstaller.okhttp.CachingAuthenticatorDecorator;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import okhttp3.*;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Setup {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {

        final PropertiesConfiguration props = Configuration.getPropertiesConfiguration();

        final DigestAuthenticator authenticator = new DigestAuthenticator(new Credentials(props.getString("USERNAME"), props.getString("PASSWORD")));

        final Map<String, CachingAuthenticator> authCache = new ConcurrentHashMap<>();
        final OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new CachingAuthenticatorDecorator(authenticator, authCache))
                .addInterceptor(new AuthenticationCacheInterceptor(authCache))
                .build();

        LOG.info(String.format("Creating a ReST instance on port %s", props.getString("PORT")));

        /*
            Example payload for this taken from: http://docs.marklogic.com/guide/rest-dev/service#id_12021
            <rest-api xmlns="http://marklogic.com/rest-api">
                <name>RESTstop</name>
                <database>Documents</database>
                <port>8020</port>
            </rest-api>
         */

        String url = "http://localhost:8002/LATEST/rest-apis";

        /*         Request request = new Request.Builder()
                .url(String.format("http://localhost:8002/manage/v2/forests/%s", Configuration.FOREST))
                .post(RequestBody.create(MediaType.parse(URLENCODED_FORM_MIMETYPE), "state=clear"))
                .build();
                */
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/xml")
                .post(RequestBody.create(MediaType.parse("application/xml"), Configuration.createRestServerXmlPayload()))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 201) {
                LOG.info(String.format("Successfully created a ReST instance on port %s", props.getString("PORT")));
            } else {
                LOG.error("Something went wrong...");
            }

//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                LOG.info(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//
//            LOG.info("Response code: " + response.code());
//            LOG.info(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
