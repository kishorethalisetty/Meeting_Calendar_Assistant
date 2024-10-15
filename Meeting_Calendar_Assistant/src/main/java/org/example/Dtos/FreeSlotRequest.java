package org.example.Dtos;
import lombok.Data;
import org.example.Pojos.Employee;

import java.time.Duration;

@Data
public class FreeSlotRequest {
    private Long employeeId1;
    private Long employeeId2;
    private Duration duration;
}
