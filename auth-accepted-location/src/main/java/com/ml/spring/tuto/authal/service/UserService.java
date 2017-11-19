package com.ml.spring.tuto.authal.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.ml.spring.tuto.authal.enums.RolesEnum;
import com.ml.spring.tuto.authal.persistence.model.NewLocationToken;
import com.ml.spring.tuto.authal.persistence.model.User;
import com.ml.spring.tuto.authal.persistence.model.UserLocation;
import com.ml.spring.tuto.authal.persistence.model.dto.UserDto;
import com.ml.spring.tuto.authal.persistence.repositoris.NewLocationTokenRepository;
import com.ml.spring.tuto.authal.persistence.repositoris.UserLocationRepository;
import com.ml.spring.tuto.authal.persistence.repositoris.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLocationRepository locationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DatabaseReader databaseReader;

    @Autowired
    private NewLocationTokenRepository newLocationRepository;

    public User create(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = new User(userDto);

        if (user.getRoles().isEmpty()) {
            user.addRole(RolesEnum.USER);
        }
        return userRepository.save(user);
    }

    public void addUserLocation(User user, String ipVN) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ipVN);
        String country = databaseReader.country(ipAddress).getCountry().getName();
        UserLocation loc = new UserLocation(country, user);
        loc.setEnabled(true);
        locationRepository.save(loc);
    }

    public UserLocation getUserLocationByUserAndCountry(User user, String country) {
        return locationRepository.findByUserAndCountry(user, country);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public NewLocationToken isNewLocation(String username, String ip) {
        try {
            final InetAddress ipAddress = InetAddress.getByName(ip);

            final String country = databaseReader.country(ipAddress).getCountry().getName();
            final User user = userRepository.findByUsername(username);
            final UserLocation userLocation = locationRepository.findByUserAndCountry(user, country);
            if (userLocation == null) {
                return createNewLocationToken(country, user);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private NewLocationToken createNewLocationToken(String country, User user) {
        UserLocation loc = new UserLocation(country, user);
        loc = locationRepository.save(loc);
        final NewLocationToken token = new NewLocationToken(UUID.randomUUID().toString(), loc);
        return newLocationRepository.save(token);
    }

    public NewLocationToken getNewLocationToken(long tokenId) {
        return newLocationRepository.findOne(tokenId);
    }

    public NewLocationToken isValidNewLocationToken(String token) {
        final NewLocationToken newLoc = newLocationRepository.findByToken(token);
        if (newLoc == null) return null;

        UserLocation loc = newLoc.getUserLocation();
        loc.setEnabled(true);
        locationRepository.save(loc);
        newLocationRepository.delete(newLoc);

        return newLoc;
    }
}
