package ch.patchcode.jback.testsInfra;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.HttpCookie;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tracks "Set-Cookie" headers in the response and applies "Cookie" headers in the requests.
 * <p>
 * This is a primitive way of allowing for session cookies.
 */
public class CookieTrackerFilter implements ExchangeFilterFunction {

    private Map<String, String> cookies = new HashMap<>();

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return Mono
                .just(request)
                .map(this::applyCookies)
                .flatMap(next::exchange)
                .map(this::filterForCookies);
    }

    private ClientRequest applyCookies(ClientRequest request) {
        ClientRequest.Builder builder = ClientRequest.from(request);
        cookies.forEach(builder::cookie);
        return builder.build();
    }

    private ClientResponse filterForCookies(ClientResponse response) {
        var headers = response.headers().asHttpHeaders();
        var cookiesToSet = headers.entrySet().stream()
                .filter(entry -> entry.getKey().equals("Set-Cookie"))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(HttpCookie::parse)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(HttpCookie::getName, HttpCookie::getValue));
        this.cookies.putAll(cookiesToSet);
        return response;
    }
}
