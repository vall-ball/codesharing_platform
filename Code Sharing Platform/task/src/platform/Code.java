package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Code {
    private String code;

    private static int nextId = 1;

    private static final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;
    //private LocalDateTime localDateTime = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    private String date;

    public Code() {
    }

    public Code(String code) {
        this.code = code;
        this.id = Integer.toString(nextId);
        nextId++;
        this.setDate();
    }

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
        this.date = LocalDateTime.now().format(formatter);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return code;
    }
}
