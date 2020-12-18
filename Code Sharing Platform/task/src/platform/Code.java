package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity(name = "code")
public class Code {
    @Column
    private String code;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Id
    private String id;
    @Column
    private String date;
    @Column
    private int time;
    @Column
    private int views;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isSecretTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isSecretViews;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public LocalDateTime begin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public LocalDateTime end;


    public Code() {
    }

    public Code(String code, int time, int views) {
        this.code = code;
        this.time = time;
        this.views = views;
        this.id = UUID.randomUUID().toString();
        this.setDate();
        this.end = begin.plusSeconds(time);
        if (time > 0) {
            this.isSecretTime = true;
        } else {
            this.isSecretTime = false;
        }
        if (views > 0) {
            this.isSecretViews = true;
        } else {
            this.isSecretViews = false;
        }
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
        this.begin = LocalDateTime.now();
        this.date = begin.format(formatter);
    }

    public String getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getViews() {
        return views;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                ", id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", views=" + views +
                ", isSecretTime=" + isSecretTime +
                ", isSecretViews=" + isSecretViews +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
