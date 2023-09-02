package lk.ijse.spacewood.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    private String order_id;
    private String itemCode;
    private int qtyDelivered;
    private String driver_id;
    private String date;
    private String location;
    private int qtyOrdered;

    public OrderDetails(String code, Integer qty) {
        this.itemCode = code;
        this.qtyOrdered = qty;

    }


}
