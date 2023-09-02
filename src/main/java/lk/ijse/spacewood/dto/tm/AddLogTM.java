package lk.ijse.spacewood.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddLogTM {
    private String id;
    private String description;
    private String type;
    private String permitNo;
    private String location;

}
