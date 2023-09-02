package lk.ijse.spacewood.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTM {
    private String orderId;

    private double cost;
    private String date;
}
