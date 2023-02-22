package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "code_snippet")
public class CodeSnippet {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonIgnore
    private String id;

    @Column(name = "code")
    private String code;
    @Column(name = "create_date")
    private String date;

    @Column(name = "time_restrict")
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "view_restrict")
    private long views;

    @Column(name="expire_date")
    @JsonIgnore
    private String expireDate;

    public CodeSnippet(String code) {
        this.code = code;
        date = formatDateTime(LocalDateTime.now());
    }

    public CodeSnippet() {
        code = "";
        date = formatDateTime(LocalDateTime.now());
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

    public void setDate(LocalDateTime dateTime) {
        this.date = formatDateTime(dateTime);
        this.expireDate= formatDateTime(dateTime.plusSeconds(time));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public String getExpireDate() {
        return expireDate;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", views=" + views +
                ", expireDate='" + expireDate + '\'' +
                '}';
    }

    @JsonIgnore
    public String typeOfRestriction(){
        if(getTime()>0 &&getViews()>0) return "both";
        if(getTime()>0) return "time";
        if(getViews()>0) return "view";
        return "";
    }

    @JsonIgnore
    public boolean isRestrict(){
        return this.typeOfRestriction().length() != 0;
    }
}