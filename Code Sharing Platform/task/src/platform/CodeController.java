package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CodeController {
    @Autowired
    public CodeService codeService;

    @GetMapping("/code/{id}")
    public String getHtml(ModelMap model, HttpServletResponse response, @PathVariable(value = "id") int id) {
        Code code = codeService.findCodeById(id);
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
    public ResponseEntity<Object> getJson(@PathVariable(value = "id") int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");
        try {
            Code code = codeService.findCodeById(id);
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(code);
        } catch (Exception e) {
            return new ResponseEntity<>("404 (Not found)", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public ResponseEntity<Object> postCode(@RequestBody Code newCode) throws IOException {
        Code code = new Code(newCode.getCode());
        codeService.save(code);
        return new ResponseEntity<>(new Idd(Integer.toString(code.getId())), HttpStatus.OK);
    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    public ResponseEntity<Object> getLatestJson() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "application/json");
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
        List<Code> temp = codeService.list();
        while (i != 11 && i <= temp.size()) {
            codes.add(temp.get(temp.size() - i));
            i++;
        }
        return codes;
    }

}
