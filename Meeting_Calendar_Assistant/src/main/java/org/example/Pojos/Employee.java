package org.example.Pojos;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Employee {

    private Long id;
    private String name;
    private List<Meeting> meetings = new ArrayList<>();

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

