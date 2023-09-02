package lk.ijse.spacewood.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Stock {
    private String woodcutId;
    private String type;
    private double length;
    private double width;
    private double height;
    private int qty;
    private String logId;
}
