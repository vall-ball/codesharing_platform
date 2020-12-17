package platform;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CodeController {
    List<Code> codeList = new ArrayList<>();
    //Code code = new Code();
/*
    @ModelAttribute("code")
    public Code newCode() {
        return code;
    }
*/
    @GetMapping("/code/{id}")
    public String getHtml(ModelMap model, HttpServletResponse response, @PathVariable(value = "id") String id) {
        Code code = null;
        for (Code c : codeList) {
            if (c.getId().equals(id)) {
                code = c;
                break;
            }
        }
        model.addAttribute("code", code);
        response.addHeader("Content-Type", "text/html");
        return "answer";
    }

    @GetMapping("/code/new")
    public String addCode() {
        return "addCode";
    }

    @GetMapping("/api/code/{id}")
    @ResponseBody
    public ResponseEntity<Object> getJson(@PathVariable(value = "id") String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");
        Code code = null;
        for (Code c : codeList) {
            if (c.getId().equals(id)) {
                code = c;
                break;
            }
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(code);
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public ResponseEntity<Object> postCode(@RequestBody Code newCode) throws IOException {
        Code code = new Code(newCode.getCode());
        ///System.out.println(newCode.code);
        codeList.add(code);
        return new ResponseEntity<>(new Id(code.getId()), HttpStatus.OK);
    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    public ResponseEntity<Object> getLatestJson() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");
       //Code[] codes = new Code[10];
       /* for (int i = codeList.size() - 10; i < codeList.size(); i++) {
            codes[i - codeList.size()] = codeList.get(i);
        }*/
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(latest());
    }

    @GetMapping("/code/latest")
    public String getLatest(ModelMap model, HttpServletResponse response) {
        model.addAttribute("codes", latest());
        response.addHeader("Content-Type", "text/html");
        return "latest";
    }

    public List<Code> latest() {
        int i = 1;
        List<Code> codes = new ArrayList<>();
        while (i != 11 && i <= codeList.size()) {
            codes.add(codeList.get(codeList.size() - i));
            i++;
        }
        return codes;
    }

}
