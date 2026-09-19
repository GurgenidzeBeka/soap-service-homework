package ge.tbc.soap.models.db;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Employee {
    private Long employeeId;
    private String name;
    private String department;
    private String phone;
    private String address;
    private BigDecimal salary;
    private String email;
    private LocalDate birthDate;
}