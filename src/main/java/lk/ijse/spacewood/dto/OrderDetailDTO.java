package lk.ijse.spacewood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailDTO {

    String itemCode;
    String driverId;
    String date;
    String location;
    int qty;
}
