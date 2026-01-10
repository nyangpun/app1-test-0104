package com.back.domain.home.controller;

import com.back.global.app.AppConfig;
import com.back.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final Rq rq;

    @GetMapping
    public String main() {
        InetAddress localHost = null;

        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        // log.info("Runs in any environment");
        log.debug("Run in dev/prod environments");

        return "main(version : 1.0.0), hostname : %s".formatted(localHost.getHostName());
    }

    @GetMapping("/cookie/{name}/{value}")
    public String setCookie(@PathVariable String name, @PathVariable String value) {
        rq.setCookie(name, value);

        return "%s=%s".formatted(name, value);
    }

    @GetMapping("/newFile")
    public String newFile() throws IOException {
        String fileName = UUID.randomUUID().toString() + ".html";
        String filePath = AppConfig.getGenFileDirPath() + "/" + fileName;

        File directory = new File(AppConfig.getGenFileDirPath());

        if(!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(filePath);

        try(FileWriter writer = new FileWriter(file)) {
            writer.write("<h1>%s</h1>".formatted(fileName));
        }

        return AppConfig.getSiteBackUrl() + "/gen/" + fileName;
    }

    @GetMapping("/session/{name}/{value}")
    public String setSession(@PathVariable String name, @PathVariable String value) {
        rq.setSession(name, value);

        return "%s=%s".formatted(name, value);
    }

    @GetMapping("/session/{name}")
    public String setSession(@PathVariable String name) {

        String sessionValue = rq.getSessionValueAsStr(name);

        return sessionValue != null ? sessionValue : "";
    }
}