package com.gmail.kruglov25ks.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/")
@SessionAttributes
public class MainControl {

    @RequestMapping("/")
    public String start() {
        return "start";
    }
}
