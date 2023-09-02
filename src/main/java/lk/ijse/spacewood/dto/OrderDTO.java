package lk.ijse.spacewood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
    String code;
    Integer qty;
}
