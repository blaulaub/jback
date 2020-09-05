package ch.patchcode.jback.main.restApi;

import org.springframework.http.ResponseEntity;

public interface RestApiInvocationResult<TSelf, TResponse> {

    TSelf checkResultIsSuccess();

    ResponseEntity<TResponse> andReturn();
}
