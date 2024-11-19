package com.github.basic.web.controller.sample;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SessionTokenSampleController {

    @GetMapping("/set-session")
    public String setSession(HttpSession session) {
        session.setAttribute("user","조인성");
        session.setAttribute("gender","남자");
        return "Session Set Successfully";
    }

    @GetMapping("/get-session")
    public String getSession(HttpSession session) {
        String user = (String) session.getAttribute("user");
        String gender = (String) session.getAttribute("gender");
        return "user = " + user + "gender = " + gender;
    }
}
