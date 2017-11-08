package com.ml.spring.tuto.aae.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class VerificationTokenTest {

    private VerificationToken token;

    @Before
    public void init() throws Exception {
        token = new VerificationToken();
    }

    @Test
    public void testExpiratedCalculateDate() throws Exception {
        int expiryTestMinutes = 60;
        Date actual = token.calculateExpiryDate(expiryTestMinutes);
        System.out.println(actual);
    }
}