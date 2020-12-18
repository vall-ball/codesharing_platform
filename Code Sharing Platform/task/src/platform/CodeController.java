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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CodeController {
    @Autowired
    public CodeService codeService;

    @GetMapping("/code/{id}")
    public String getHtml(ModelMap model, HttpServletResponse response, @PathVariable(value = "id") String id) {
        try {
        Code code = codeService.findCodeById(id);
        System.out.println(code == null);
        model.addAttribute("code", code);
        System.out.println(code);
        if (code.isSecretViews) {
            int views = code.getViews();
            views--;
            code.setViews(views);
            if (views <= 0) {
                System.out.println(" if (time <= 0) {");
                codeService.delete(code.getId());
            }
        }
        if (code.isSecretTime) {
            System.out.println(" first   } else {");
            LocalDateTime now = LocalDateTime.now();
            //long seconds = code.dateTime.until(now, ChronoUnit.SECONDS );
            if (code.end.isBefore(now)) {
                System.out.println("  if (seconds <= 0) {");
                codeService.delete(code.getId());
            } else {
                System.out.println("second } else {");
                code.setTime((int) now.until(code.end, ChronoUnit.SECONDS));
            }
        }
        codeService.save(code);
        response.addHeader("Content-Type", "text/html");
        return "answer";
        } catch (Exception e) {
                response.setStatus(404);
                return "error";
        }
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
        try {
            Code code = codeService.findCodeById(id);
            ResponseEntity response = ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(code);
            System.out.println(code);
            if (code.isSecretViews) {
                int views = code.getViews();
                views--;
                code.setViews(views);
                if (views <= 0) {
                    System.out.println(" if (time <= 0) {");
                    codeService.delete(code.getId());
                    return new ResponseEntity<>("404 (Not found)", HttpStatus.NOT_FOUND);
                }
            }
                if (code.isSecretTime) {
                    System.out.println(" first   } else {");
                    LocalDateTime now = LocalDateTime.now();
                    //long seconds = code.dateTime.until(now, ChronoUnit.SECONDS );
                    if (code.end.isBefore(now)) {
                        System.out.println("  if (seconds <= 0) {");
                        codeService.delete(code.getId());
                        return new ResponseEntity<>("404 (Not found)", HttpStatus.NOT_FOUND);
                    } else {
                        System.out.println("second } else {");
                        code.setTime((int) now.until(code.end, ChronoUnit.SECONDS));
                    }
                }
            codeService.save(code);
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>("404 (Not found)", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public ResponseEntity<Object> postCode(@RequestBody Code newCode) throws IOException {
        Code code = new Code(newCode.getCode(), newCode.getTime(), newCode.getViews());
        codeService.save(code);
        return new ResponseEntity<>(new Idd(code.getId()), HttpStatus.OK);
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
