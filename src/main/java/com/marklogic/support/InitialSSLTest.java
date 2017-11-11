package com.marklogic.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.security.KeyStore;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class InitialSSLTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Configuration config;
    //private static SecurityOptions so;
    private static String xccUrl;

    public static void main(String[] args) {

        xccUrl = config.getString("SSL_XCC_URI");
        LOG.debug("SSL URI: " + xccUrl);
        String certLocation = config.getString("JKS_FILE");
        LOG.debug("Certificate Location is: " + certLocation);
        /*
        so = newTrustOptions(certLocation);
        so.setEnabledCipherSuites(new String[]{"TLS_RSA_WITH_AES_128_CBC_SHA"});
        so.setEnabledProtocols(new String[]{"TLSv1.2"}); */

    }

    TrustManager naiveTrustMgr[] = new X509TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }
    };

    /*
    private static SecurityOptions newTrustOptions(String certLocation) throws Exception {
        KeyStore clientKeyStore = KeyStore.getInstance("JKS");
        clientKeyStore.load(new FileInputStream(certLocation), config.getString("KEY_PASSWD").toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(clientKeyStore, config.getString("KEY_PASSWD").toCharArray());
        KeyManager[] key = keyManagerFactory.getKeyManagers();
        SSLContext sslContext = SSLContext.getInstance("SSLv3");
        sslContext.init(key, getTrust(), (SecureRandom) null);
        return new SecurityOptions(sslContext);
    } */
}
