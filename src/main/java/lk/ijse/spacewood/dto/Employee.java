package lk.ijse.spacewood.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    private String id;
    private String firstName;
    private String nic;
    private String address;
    private String phone;
    private String lastName;
    private String whatsapp;
    private String dateJoined;
    private String dateLeft;

}
