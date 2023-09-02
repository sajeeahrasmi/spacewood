package lk.ijse.spacewood.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Item {
    private String itemCode;
    private String description;
    private double unitPrice;
}
