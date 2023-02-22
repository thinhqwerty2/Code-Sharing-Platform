package platform.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.model.CodeService;
import platform.model.CodeSnippet;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;


@RestController
@RequestMapping("/api/code")
public class ApiCodeController {

    private final CodeService codeService;

    public ApiCodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CodeSnippet> getApiCode(@PathVariable String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        CodeSnippet ans = codeService.getCodeById(id);
        System.out.println(ans + LocalDateTime.now().toString());
        if (ans != null) {

            if (updateCode(formatter, ans, codeService))
                return ResponseEntity.ok().headers(responseHeaders).body(ans);
        }
        return ResponseEntity.notFound().headers(responseHeaders).build();

    }

    static boolean updateCode(DateTimeFormatter formatter, CodeSnippet ans, CodeService codeService) {
        long reserveTime = Duration.between(now(), parse(ans.getExpireDate(), formatter)).getSeconds();
        switch (ans.typeOfRestriction()) {
            case "time" -> {
                ans.setTime(reserveTime);
                if (ans.getTime() < 0) {
                    ans.setTime(0);
                    codeService.deleteCode(ans);
                    return false;
                } else {
                    codeService.update(ans);
                    return true;
                }
            }
            case "view" -> {
                ans.setViews(ans.getViews() - 1);
                if (ans.getViews() < 0) {
                    ans.setViews(0);
                    codeService.deleteCode(ans);
                    return false;

                } else {
                    if (ans.getViews() == 0) {
                        codeService.deleteCode(ans);
                    } else
                        codeService.update(ans);
                    return true;
                }
            }
            case "both" -> {
                ans.setTime(reserveTime);
                if (ans.getTime() < 0) {
                    ans.setTime(0);
                    codeService.deleteCode(ans);
                    return false;
                } else {
                    ans.setViews(ans.getViews() - 1);
                    if (ans.getViews() < 0) {
                        ans.setViews(0);
                        codeService.deleteCode(ans);
                        return false;
                    } else {
                        codeService.update(ans);
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @GetMapping("/latest")
    @ResponseBody
    public ResponseEntity<ArrayList<CodeSnippet>> latestCodes() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.ok().headers(responseHeaders).body(new ArrayList<>(codeService.get10LatestCode()));
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<Map<String, String>> setApiCode(@RequestBody CodeSnippet code) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        String id = codeService.saveCode(code);
        return ResponseEntity.ok().headers(responseHeaders).body(Map.of("id", id));
    }
}