package moe.sayuri.nana.core.service;

import lombok.extern.slf4j.Slf4j;
import moe.sayuri.nana.core.exception.BaseException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
    public String test() {
        log.info("test log");
        return "test";
    }

    public String error() {
        log.error("error log");
        throw BaseException.wrap(new RuntimeException("error"));
    }
}
