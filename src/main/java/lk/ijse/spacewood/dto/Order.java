package lk.ijse.spacewood.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Order {
    private String order_id;
    private String customerId;
    private String date;
    private  double total;
}
