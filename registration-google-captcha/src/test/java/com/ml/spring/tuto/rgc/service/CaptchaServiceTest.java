package com.ml.spring.tuto.rgc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CaptchaServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CaptchaService captchaService;


    @Test
    public void testMatchCaptcha() throws Exception {
        String strCaptcha = "13232";
        IntStream.range(0, 6).forEach(it -> {
            System.out.println("Try reCaptcha " + it);
            try {
                captchaService.processResponse(strCaptcha);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(1000);
    }

}