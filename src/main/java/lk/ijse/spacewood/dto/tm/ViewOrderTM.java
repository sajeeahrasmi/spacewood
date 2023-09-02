package lk.ijse.spacewood.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ViewOrderTM {
    private String code;

    private Integer itemNumber;
    private String driver;
    private String date;
    private String location;

    private int qty;
}
