package moe.sayuri.nana.rest.controller;

import moe.sayuri.nana.core.service.TestService;
import moe.sayuri.nana.rest.access.ApiReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nana/api")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public ApiReturn<String> test() {
        return new ApiReturn<>(testService.test());
    }

    @GetMapping("/error")
    public ApiReturn<String> error() {
        return new ApiReturn<>(testService.error());
    }
}
