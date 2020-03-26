package com.shawn.touchstone.composite;

import java.util.List;

public class HRDemo {

    public static final long ORG_ID = 1001L;

    private DepartmentRepo departmentRepo;

    private EmployeeRepo employeeRepo;

    public void buildOrg(Long id) {
        buildOrg(new Department(id));
    }

    public void buildOrg(Department department) {
        List<Long> subDepartmentIds = departmentRepo.getSubDepartmentByIds(department.getId());
        for (Long id: subDepartmentIds) {
            Department subDepartment = new Department(id);
            department.addNode(subDepartment);
            buildOrg(subDepartment);
        }
        List<Long> employees = employeeRepo.getDepartmentEmployeeIds(department.getId());
        for (Long employeeId : employees) {
            double salary = employeeRepo.getEmployeeSalary(employeeId);
            department.addNode(new Employee(employeeId, salary));
        }
    }

    private class DepartmentRepo {
        public List<Long> getSubDepartmentByIds(long id) {
            throw new RuntimeException();
        }
    }

    private class EmployeeRepo {
        public List<Long> getDepartmentEmployeeIds(long departmentId) {
            throw new RuntimeException();
        }

        public double getEmployeeSalary(Long employeeId) {
            throw new RuntimeException();
        }
    }
}
