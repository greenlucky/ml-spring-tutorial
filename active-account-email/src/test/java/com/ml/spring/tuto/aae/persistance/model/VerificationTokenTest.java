package com.ml.spring.tuto.aae.persistance.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class VerificationTokenTest {

    private VerificationToken token;

    @Before
    public void init() throws Exception {
        token = new VerificationToken();
    }

    @Test
    public void testExpiratedCalculateDate() throws Exception {
        int expiryTestMinutes = 60;
        long aspect = new Date().getTime() + 60 * 60 * 1000;
        long actual = token.calculateExpiryDate(expiryTestMinutes);
        assertEquals(aspect, actual);
    }
}