package ch.patchcode.jback.main.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RestSession {

    private final int port;

    private final TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final Map<String, String> cookies = new HashMap<>();

    public RestSession(int port, TestRestTemplate restTemplate, ObjectMapper objectMapper) {
        this.port = port;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    public <TReq, TResp> ResponseEntity<TResp> post(
            String url,
            TReq requestData,
            Class<TResp> responseClass
    ) throws Exception {

        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestData), headers());
        restTemplate.getRestTemplate().setErrorHandler(errorHandler());
        ResponseEntity<TResp> result = restTemplate.exchange(
                baseUrl() + url,
                HttpMethod.POST,
                requestEntity,
                responseClass);
        setCookies(result);
        return result;
    }

    public <TReq> ResponseEntity<Void> put(String url, TReq requestData) throws Exception {

        ResponseEntity<Void> result = restTemplate.exchange(
                baseUrl() + url,
                HttpMethod.PUT,
                new HttpEntity<>(objectMapper.writeValueAsString(requestData), headers()),
                Void.class);
        setCookies(result);
        return result;
    }

    public <TResp> ResponseEntity<TResp> get(String url, Class<TResp> responseClass) {

        ResponseEntity<TResp> result = restTemplate.exchange(
                baseUrl() + url,
                HttpMethod.GET,
                new HttpEntity<>(headers()),
                responseClass);
        setCookies(result);
        return result;
    }

    private HttpHeaders headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (cookies.size() > 0) {
            httpHeaders.set("Cookie", String.join("; ", cookies.values()));
        }
        return httpHeaders;
    }

    private void setCookies(ResponseEntity<?> result) {
        Optional.ofNullable(result.getHeaders().get("Set-Cookie")).ifPresent(
                cookies -> cookies.stream()
                        .map(HttpCookie::parse)
                        .flatMap(Collection::stream)
                        .forEach(cookie -> this.cookies.put(cookie.getName(), cookie.toString())));
    }

    private ResponseErrorHandler errorHandler() {

        return new ResponseErrorHandler() {

            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().isError();
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                var statusCode = response.getStatusCode();
                if (statusCode == HttpStatus.FORBIDDEN) {
                    throw new ForbiddenException();
                }
            }
        };
    }

    public static class ForbiddenException extends RuntimeException {
    }
}
