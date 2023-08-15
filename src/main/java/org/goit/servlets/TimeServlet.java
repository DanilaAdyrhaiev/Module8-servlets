package org.goit.servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    String time;
    ZoneId zoneId;
    String val;
    DateTimeFormatter formatter;

    @Override
    public void init() throws ServletException {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("service{");
        resp.setContentType("text/html; charset=UTF-8");
        log(req.getRequestURL().toString());
        log(req.getQueryString());
        val = req.getParameter("timezone");
        log(val);

        if(val !=  null && !val.isEmpty()){
            val = val.replace(" ", "+");
            log(val);
            zoneId = ZoneId.of(val);
            log(zoneId.toString());
        }
        else{
            zoneId = ZoneId.of("UTC");
        }

        ZonedDateTime zoneTime = ZonedDateTime.now(zoneId);
        time = zoneTime.format(formatter);

        resp.getWriter().write(time);
        resp.getWriter().close();
        log("}service");
    }

    @Override
    public void destroy() {
        time = null;
    }
}
