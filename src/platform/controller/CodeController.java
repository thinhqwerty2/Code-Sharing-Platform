package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import platform.exceptions.CodeNotFoundException;
import platform.model.CodeRepository;
import platform.model.CodeService;
import platform.model.CodeSnippet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static java.time.LocalDateTime.*;

@Controller
public class CodeController {


    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/{id}")
    public String getCode(Model model, @PathVariable String id) {
        CodeSnippet ans = codeService.getCodeById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        if (ans != null) {
        model.addAttribute("restrictView", ans.typeOfRestriction().equals("view"));
            System.out.println("before" + ans + LocalDateTime.now());
            boolean check = ApiCodeController.updateCode(formatter, ans, codeService);
            if (check) {
                System.out.println("after" + ans + LocalDateTime.now());
                model.addAttribute("code_snippet", ans);
                return "code";
            }
        }
        throw new CodeNotFoundException();
//        model.addAttribute("code_snippet", new CodeSnippet("Not Found"));
//        return "code";
    }

    @GetMapping("/code/latest")
    public String getHtmlCode(Model model) {
        ArrayList<CodeSnippet> ans = new ArrayList<>(codeService.get10LatestCode());
        model.addAttribute("listCode", ans);

        return "latest-code";
    }

    @GetMapping("/code/new")
    public String getCodeForm(Model model) {
        return "new-code";
    }

}