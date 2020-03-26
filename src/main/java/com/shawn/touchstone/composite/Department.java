package com.shawn.touchstone.composite;

import java.util.ArrayList;
import java.util.List;

public class Department extends HumanResource {

    private List<HumanResource> nodes;

    public Department(long id) {
        super(id);
        this.nodes = new ArrayList<>();
    }

    @Override
    public double calculateSalary() {
        double totalSalary = 0;
        for (HumanResource hr: nodes) {
            totalSalary += hr.calculateSalary();
        }
        this.salary = totalSalary;
        return totalSalary;
    }

    public void addNode(HumanResource humanResource) {
        nodes.add(humanResource);
    }

}
