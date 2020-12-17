package platform;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CodeController {
    Code code = new Code();

    @ModelAttribute("code")
    public Code newCode() {
        return code;
    }

    @GetMapping("/code")
    public String getHtml(ModelMap model, HttpServletResponse response) {
        model.addAttribute("code",code);
        response.addHeader("Content-Type", "text/html");
        return "answer";
    }

    @GetMapping("/code/new")
    public String addCode() {
        return "addCode";
    }

    @GetMapping("/api/code")
    @ResponseBody
    public ResponseEntity<Object> getJson() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(code);
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public ResponseEntity<Object> postCode(@RequestBody Code newCode) throws IOException {
        newCode.setDate();
        code = newCode;
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
