package org.example.ServiceLayer;

import org.example.Dtos.MeetingRequest;
import org.example.Pojos.Employee;
import org.example.Pojos.Meeting;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MeetingService {

    private Map<Long, Employee> employeeMap = new HashMap<>();
    private Map<Long, Meeting> meetingMap = new HashMap<>();
    private Long meetingIdCounter = 1L;


    public void addEmployee(Long id, String name) {
        Employee employee = new Employee(id, name);
        employeeMap.put(id, employee);
    }

    // Booking a meeting for the owner of the calendar
    public void bookMeeting(Long ownerId, MeetingRequest meetingRequest) {
        Employee owner = employeeMap.get(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Employee not found");
        }
        if(LocalDateTime.now().isAfter(meetingRequest.getStartTime()) ||
                meetingRequest.getEndTime().isBefore(meetingRequest.getStartTime()) ||
           meetingRequest.getStartTime().isAfter(meetingRequest.getEndTime())){
            throw new IllegalArgumentException("Starting &  Ending timing is not in Order");
        }

        Meeting meeting = new Meeting(meetingIdCounter++, meetingRequest.getStartTime(), meetingRequest.getEndTime(), ownerId);

        List<Long> participantIds = meetingRequest.getParticipantIds();

        meeting.setParticipants(participantIds);
        owner.getMeetings().add(meeting);

        for (Long participantId : participantIds) {
            Employee participant=getEmployee(participantId);
            participant.getMeetings().add(meeting);
        }

        meetingMap.put(meeting.getId(), meeting);
    }

    // Finding free slots for two employees
    public List<LocalDateTime[]> findFreeSlots(Long employeeId1, Long employeeId2, Duration meetingDuration) {
        List<Meeting> allMeetings = new ArrayList<>();
        Employee employee1=getEmployee(employeeId1);
        Employee employee2=getEmployee(employeeId2);
        allMeetings.addAll(employee1.getMeetings());
        allMeetings.addAll(employee2.getMeetings());

        Collections.sort(allMeetings,(m1,m2)->m1.getStartTime().compareTo( m2.getStartTime()));

        List<LocalDateTime[]> freeSlots = new ArrayList<>();
        LocalDateTime startOfDay = LocalDateTime.now();

        LocalDateTime lastEndTime = startOfDay;
        for (Meeting meeting : allMeetings) {
            if (Duration.between(lastEndTime, meeting.getStartTime()).compareTo(meetingDuration) >= 0) {
                freeSlots.add(new LocalDateTime[]{lastEndTime, meeting.getStartTime()});
            }
            lastEndTime = meeting.getEndTime();
        }

        freeSlots.add(new LocalDateTime[]{lastEndTime,lastEndTime.plus(meetingDuration)});

        return freeSlots;
    }

    // Checking for meeting conflicts for participants
    public List<Employee> checkForConflicts(MeetingRequest meetingRequest) {
        List<Employee> conflictingEmployees = new ArrayList<>();

        for (Long participantId : meetingRequest.getParticipantIds()) {
            Employee participant = employeeMap.get(participantId);
            if (participant == null) {
                throw new IllegalArgumentException("Employee not found");
            }

            for (Meeting meeting : participant.getMeetings()) {
                if (meeting.getStartTime().isBefore(meetingRequest.getEndTime()) &&
                        meeting.getEndTime().isAfter(meetingRequest.getStartTime())) {
                    conflictingEmployees.add(participant);
                    break;
                }
            }
        }

        return conflictingEmployees;
    }

    // Retrieve employees for API purposes
    public Employee getEmployee(Long employeeId) {
        return employeeMap.get(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }
}

