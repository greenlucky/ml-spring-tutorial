package com.ml.spring.tuto.authal.config;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Configuration
public class ApplicationConfig {

    @Bean
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
        File resource = new File("/Users/lamdevops/Documents/MyProjects/ml-spring-tutorial/auth-accepted-location/src/main/resources/static/database/GeoLite2-Country.mmdb");
        return new DatabaseReader.Builder(resource).build();
    }

    @Bean
    public String clientIPAddress() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof NativeWebRequest) {
            HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
            return request.getRemoteAddr();
        }
        return "113.161.88.181";
    }
}
