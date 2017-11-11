package com.marklogic.support;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.TextDocumentManager;
import com.marklogic.client.io.StringHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.security.KeyStore;

public class APITest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws Exception {

        // config.getString("JKS_FILE")
        FileInputStream mlca = new FileInputStream("keystore.jks");
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(mlca, "password".toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
        trustManagerFactory.init(ks);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        DatabaseClient client = DatabaseClientFactory.newClient(
                "localhost", 9996, "Documents", "q", "q", DatabaseClientFactory.Authentication.DIGEST, sslContext, DatabaseClientFactory.SSLHostnameVerifier.COMMON);

        // Do some work

        LOG.info("Connected to: " + client.getDatabase());
        LOG.info("Connected on: " + client.getPort());
        //client.newLogger().
        //client.newQueryManager()

        client.release();

    }

}