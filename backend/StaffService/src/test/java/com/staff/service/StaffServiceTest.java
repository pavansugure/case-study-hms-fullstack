package com.staff.service;

import com.staff.exception.ResourceNotFoundException;
import com.staff.model.Staff;
import com.staff.repo.StaffRepository;
import com.staff.service.impl.StaffServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffServiceImpl staffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testCreateStaffSuccessfully() {
        // Arrange
        Staff staff = new Staff(null, "John Doe", "123 Main St", 123456789L, 50000.0, 30, "Manager", "john.doe@example.com");
        when(staffRepository.save(any(Staff.class))).thenAnswer(invocation -> {
            Staff savedStaff = invocation.getArgument(0);
            savedStaff.setCode(1L); // Simulate auto-generated ID
            return savedStaff;
        });

        // Act
        Staff createdStaff = staffService.createStaff(staff);

        // Assert
        assertNotNull(createdStaff);
        assertEquals(1L, createdStaff.getCode());
        assertEquals("John Doe", createdStaff.getEmployeeName());
        verify(staffRepository, times(1)).save(staff);
    }

    @Test
    void testGetStaffByCodeSuccessfully() {
        // Arrange
        Staff staff = new Staff(1L, "Jane Doe", "456 Elm St", 987654321L, 60000.0, 28, "Developer", "jane.doe@example.com");
        when(staffRepository.findById(1L)).thenReturn(Optional.of(staff));

        // Act
        Staff fetchedStaff = staffService.getStaffByCode(1L);

        // Assert
        assertNotNull(fetchedStaff);
        assertEquals("Jane Doe", fetchedStaff.getEmployeeName());
        verify(staffRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStaffByCodeNotFound() {
        // Arrange
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> staffService.getStaffByCode(1L));
        assertEquals("Staff not found with code: 1", exception.getMessage());
        verify(staffRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllStaffSuccessfully() {
        // Arrange
        List<Staff> staffList = new ArrayList<>();
        staffList.add(new Staff(1L, "John Doe", "123 Main St", 123456789L, 50000.0, 30, "Manager", "john.doe@example.com"));
        staffList.add(new Staff(2L, "Jane Smith", "456 Elm St", 987654321L, 60000.0, 28, "Developer", "jane.smith@example.com"));

        when(staffRepository.findAll()).thenReturn(staffList);

        // Act
        List<Staff> fetchedStaffList = staffService.getAllStaff();

        // Assert
        assertNotNull(fetchedStaffList);
        assertEquals(2, fetchedStaffList.size());
        verify(staffRepository, times(1)).findAll();
    }

    @Test
    void testDeleteStaffSuccessfully() {
        // Arrange
        when(staffRepository.existsById(1L)).thenReturn(true);
        doNothing().when(staffRepository).deleteById(1L);

        // Act
        staffService.deleteStaff(1L);

        // Assert
        verify(staffRepository, times(1)).existsById(1L);
        verify(staffRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStaffNotFound() {
        // Arrange
        when(staffRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> staffService.deleteStaff(1L));
        assertEquals("Staff not found with code: 1", exception.getMessage());
        verify(staffRepository, times(1)).existsById(1L);
        verify(staffRepository, never()).deleteById(anyLong());
    }
}
