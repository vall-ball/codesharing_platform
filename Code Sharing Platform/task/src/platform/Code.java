package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(name = "code")
public class Code {
    @Column
    private String code;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Column
    private String date;


    public Code() {
    }

    public Code(String code) {
        this.code = code;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.date = LocalDateTime.now().format(formatter);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return code;
    }
}
