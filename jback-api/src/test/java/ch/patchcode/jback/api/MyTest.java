package ch.patchcode.jback.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@ApiTestConfiguration.Apply
class MyTest {

    private final MockMvc mvc;

    @Autowired
    public MyTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void test() {

    }
}
