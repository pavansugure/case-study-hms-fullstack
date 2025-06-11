package com.staff.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.staff.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>{

}
