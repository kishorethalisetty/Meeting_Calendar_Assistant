package org.example.ControllerLayer;


import org.example.Dtos.FreeSlotRequest;
import org.example.Dtos.MeetingRequest;
import org.example.Pojos.Employee;
import org.example.ServiceLayer.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/book/{ownerId}")
    public ResponseEntity<String> bookMeeting(@PathVariable Long ownerId, @RequestBody MeetingRequest meetingRequest) {
        meetingService.bookMeeting(ownerId, meetingRequest);
        return ResponseEntity.ok("Meeting booked successfully");
    }

    @PostMapping("/find-free-slots")
    public ResponseEntity<List<LocalDateTime[]>> findFreeSlots(@RequestBody FreeSlotRequest request) {
        List<LocalDateTime[]> freeSlots = meetingService.findFreeSlots(request.getEmployeeId1(), request.getEmployeeId2(), request.getDuration());
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/check-conflicts")
    public ResponseEntity<List<Employee>> checkForConflicts(@RequestBody MeetingRequest meetingRequest) {
        List<Employee> conflictingEmployees = meetingService.checkForConflicts(meetingRequest);
        return ResponseEntity.ok(conflictingEmployees);
    }

    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestParam Long id, @RequestParam String name) {
        meetingService.addEmployee(id, name);
        return ResponseEntity.ok("Employee added successfully");
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(meetingService.getAllEmployees());
    }
}

