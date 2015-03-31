package lab.mp.observer;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.FactoryBean;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;


public class TrustSelfSignedCertHttpClientFactory implements FactoryBean<HttpClient> {

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Class<?> getObjectType() {
        return HttpClient.class;
    }

    @Override
    public HttpClient getObject() throws Exception {

        // provide SSLContext that allows self-signed certificate
        SSLContext sslContext =
                new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build();

        SSLConnectionSocketFactory sslConnectionSocketFactory
                = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER // Not allowed in production!!!.
        );

        // based on HttpClients.createSystem()
        return HttpClientBuilder.create()
                .useSystemProperties()
                .setSSLSocketFactory(sslConnectionSocketFactory)  // add custom config
                .build();
    }
}
