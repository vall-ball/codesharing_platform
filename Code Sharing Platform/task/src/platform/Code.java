package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Code {
    String code = "public class CodeSharingPlatform {\n" +
            "    public static void main(String[] args) {\n" +
            "        SpringApplication.run(CodeSharingPlatform.class, args);\n" +
            "    }";
    private static final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
    private LocalDateTime localDateTime = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    String date = localDateTime.format(formatter);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        localDateTime = LocalDateTime.now();
        this.date = localDateTime.format(formatter);
    }

    @Override
    public String toString() {
        return code;
    }
}
