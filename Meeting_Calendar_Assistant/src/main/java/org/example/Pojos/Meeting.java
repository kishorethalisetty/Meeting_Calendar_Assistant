package org.example.Pojos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Meeting {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long ownerId;
    private List<Long> participants = new ArrayList<>();

    public Meeting(Long id, LocalDateTime startTime, LocalDateTime endTime, Long ownerId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ownerId = ownerId;
    }
}
