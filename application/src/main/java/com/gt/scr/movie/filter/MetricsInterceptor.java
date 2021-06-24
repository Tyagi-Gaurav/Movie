package com.gt.scr.movie.filter;

import com.gt.scr.movie.metrics.EndpointRequestCounter;
import com.gt.scr.movie.metrics.EndpointHistogram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Component
public class MetricsInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsInterceptor.class);

    @Autowired
    private EndpointRequestCounter endpointRequestCounter;

    @Autowired
    private EndpointHistogram endpointHistogram;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        request.setAttribute("StartTime", Instant.now());
        endpointRequestCounter.increment(method, path);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Instant startTime = (Instant)request.getAttribute("StartTime");
        long duration = Instant.now().toEpochMilli() - startTime.toEpochMilli();

        LOG.info("Duration of request was : {} milli seconds", duration);

        endpointHistogram.observe(duration);
    }
}
