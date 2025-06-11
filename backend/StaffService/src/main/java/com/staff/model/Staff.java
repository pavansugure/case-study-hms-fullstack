package com.staff.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

@Entity
public class Staff {

    @Id
    @NotNull(message = "Code is required.")
    private Long code;

    @NotBlank(message = "Employee name is required.")
    private String employeeName;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotNull(message = "NIC is required.")
    @Positive(message = "NIC must be a positive number.")
    @Min(value = 1000000000L, message = "NIC must be exactly 10 digits.")
    @Max(value = 9999999999L, message = "NIC must be exactly 10 digits.")
    private Long nic;

    @NotNull(message = "Salary is required.")
    @Positive(message = "Salary must be greater than zero.")
    private Double salary;

    @Min(value = 18, message = "Age must be at least 18.")
    @Max(value = 65, message = "Age must be less than or equal to 65.")
    private int age;

    @NotBlank(message = "Occupation is required.")
    private String occupation;

    @Email(message = "Email must be a valid format.")
    private String email;

    // Getters and Setters
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getNic() {
        return nic;
    }

    public void setNic(Long nic) {
        this.nic = nic;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Constructors
    public Staff() {
    }

    public Staff(Long code, String employeeName, String address, Long nic, Double salary, int age,
                 String occupation, String email) {
        this.code = code;
        this.employeeName = employeeName;
        this.address = address;
        this.nic = nic;
        this.salary = salary;
        this.age = age;
        this.occupation = occupation;
        this.email = email;
    }
}
