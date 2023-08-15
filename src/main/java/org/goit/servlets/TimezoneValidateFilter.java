package org.goit.servlets;

import java.io.IOException;
import java.time.ZoneId;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezoneParam = req.getQueryString();

        if (timezoneParam == null || isValid(timezoneParam)){
            chain.doFilter(req, res);
        }else{
            res.setStatus(400);
            res.setContentType("text/plain;charset=UTF-8");
            res.getWriter().write("Invalid timezone");
            res.getWriter().close();
        }
    }

    private boolean isValid(String timezone){
        try {
            StringBuilder val = new StringBuilder(timezone);
            val.delete(0, 9);
            ZoneId.of(val.toString());
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
