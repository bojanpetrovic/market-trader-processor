package lab.mp.observer;

import lab.mp.model.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Observer that is capable of sending reports to exposed REST service.
 */
public class RESTMessageObserver implements MessageObserver {

    @Override
    public void report(List<Conversion> conversions) {

        HttpEntity<List<Conversion>> request = new HttpEntity<>(conversions, this.headers);

        this.restTemplate.postForObject(this.url, request, List.class);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {

        this.token = token;
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", token);
    }

    private String url;
    private String token;

    private HttpHeaders headers;

    @Autowired
    private RestTemplate restTemplate;
}
