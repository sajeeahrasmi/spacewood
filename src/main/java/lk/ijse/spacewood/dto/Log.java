package lk.ijse.spacewood.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Log {
    private String logId;
    private String type;
    private String supId;
    private String location;

    private String permitNo;
    private String description;
    private String date;
    private String used;

    public Log(String logId, String type, String permitNo, String location, String date) {
        this.logId = logId;
        this.type = type;
        this.location = location;
        this.permitNo = permitNo;
        this.date = date;
    }

    public Log(String logId,String type,String supId,String location,String permitNo,String description,String date ){
        this.logId  = logId;
        this.type = type;
        this.supId = supId;
        this.location = location;
        this.permitNo = permitNo;
        this.description = description;
        this.date = date;
    }

    public Log(String logId, String type, String supId, String location, String permitNo, String used) {
        this.logId  = logId;
        this.type = type;
        this.supId = supId;
        this.location = location;
        this.permitNo = permitNo;
        this.used = used;

    }
}
