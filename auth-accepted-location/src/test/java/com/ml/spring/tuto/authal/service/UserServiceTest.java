package com.ml.spring.tuto.authal.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.ml.spring.tuto.authal.BaseTest;
import com.ml.spring.tuto.authal.persistence.model.User;
import com.ml.spring.tuto.authal.persistence.model.UserLocation;
import com.ml.spring.tuto.authal.persistence.model.dto.UserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseTest{

    @Autowired
    private UserService userService;

    @Autowired
    private DatabaseReader databaseReader;

    @Test
    public void testUserLocationVN() throws Exception {
        String ipVN = "113.161.88.181";
        addUserLocation(ipVN);
    }

    @Test
    public void testUserLocationUS() throws Exception {
        String ipUS = "70.32.89.160";
        addUserLocation(ipUS);
    }

    private void addUserLocation(String ipVN) throws IOException, GeoIp2Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamit86");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");
        final User user = userService.create(userDto);
        userService.addUserLocation(user, ipVN);

        InetAddress ipAddress = InetAddress.getByName(ipVN);
        String country = databaseReader.country(ipAddress).getCountry().getName();

        UserLocation actualUserLoc = userService.getUserLocationByUserAndCountry(user, country);

        assertEquals(country, actualUserLoc.getCountry());
    }

}