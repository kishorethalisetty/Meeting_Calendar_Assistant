package org.example.Dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Long> participantIds;
}

