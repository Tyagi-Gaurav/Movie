package com.toptal.scr.tz.test.resource;

import com.toptal.scr.tz.test.config.TimeZoneAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeZoneHealthCheckResource extends AbstractResource {

    @Autowired
    private TimeZoneAppConfig timeZoneAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void invokeStatus() {
        String fullUrl = getFullUrl(timeZoneAppConfig.host().trim(),
                "/status", timeZoneAppConfig.port());
        responseHolder.setResponse(this.get(fullUrl, String.class));
    }
}