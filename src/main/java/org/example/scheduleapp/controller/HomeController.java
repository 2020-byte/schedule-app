package org.example.scheduleapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String home() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Schedule App</title>
                </head>
                <body>
                    <h1>Welcome to Schedule App</h1>
                    <p>H2 Console: <a href="/h2-console">Access Database</a></p>
                </body>
                </html>
                """;
    }
}
