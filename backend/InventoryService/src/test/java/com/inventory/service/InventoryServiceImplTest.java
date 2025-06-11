package com.inventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Inventory;
import com.inventory.repo.InventoryRepository;
import com.inventory.service.impl.InventoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory1;
    private Inventory inventory2;

    @BeforeEach
    public void setUp() {
        // Initialize some sample Inventory objects for testing.
        inventory1 = new Inventory(1001L, "Broom", "Cleaning device", 299.0, 20);
        inventory2 = new Inventory(1002L, "Mop", "Cleaning equipment", 199.0, 10);
    }

    @Test
    public void testAddItem() {
        // Arrange
        when(inventoryRepository.save(inventory1)).thenReturn(inventory1);

        // Act
        Inventory createdItem = inventoryService.addItem(inventory1);

        // Assert
        assertNotNull(createdItem, "The created item should not be null");
        assertEquals(inventory1.getCode(), createdItem.getCode(), "The codes should be equal");
        verify(inventoryRepository, times(1)).save(inventory1);
    }

    @Test
    public void testGetItemByCodeFound() {
        // Arrange
        when(inventoryRepository.findById(1001L)).thenReturn(Optional.of(inventory1));

        // Act
        Inventory foundItem = inventoryService.getItemByCode(1001L);

        // Assert
        assertNotNull(foundItem, "The returned item should not be null");
        assertEquals(1001L, foundItem.getCode(), "The code should match");
        verify(inventoryRepository, times(1)).findById(1001L);
    }

    @Test
    public void testGetItemByCodeNotFound() {
        // Arrange
        when(inventoryRepository.findById(1003L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, 
                () -> inventoryService.getItemByCode(1003L),
                "Expected getItemByCode to throw ResourceNotFoundException");
        assertTrue(thrown.getMessage().contains("Item not found with code: 1003"));
        verify(inventoryRepository, times(1)).findById(1003L);
    }

    @Test
    public void testGetAllItems() {
        // Arrange
        List<Inventory> items = Arrays.asList(inventory1, inventory2);
        when(inventoryRepository.findAll()).thenReturn(items);

        // Act
        List<Inventory> result = inventoryService.getAllItems();

        // Assert
        assertEquals(2, result.size(), "The result list size should be 2");
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteItemFound() {
        // Arrange
        Long code = inventory1.getCode();
        when(inventoryRepository.existsById(code)).thenReturn(true);

        // Act
        inventoryService.deleteItem(code);

        // Assert
        verify(inventoryRepository, times(1)).existsById(code);
        verify(inventoryRepository, times(1)).deleteById(code);
    }

    @Test
    public void testDeleteItemNotFound() {
        // Arrange
        Long code = 1001L;
        when(inventoryRepository.existsById(code)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, 
                () -> inventoryService.deleteItem(code),
                "Expected deleteItem to throw ResourceNotFoundException");
        assertTrue(thrown.getMessage().contains("Item not found with code: " + code));
        verify(inventoryRepository, times(1)).existsById(code);
        verify(inventoryRepository, never()).deleteById(code);
    }

    @Test
    public void testUpdateItemFound() {
        // Arrange
        // Assume the item exists.
        when(inventoryRepository.existsById(inventory1.getCode())).thenReturn(true);
        when(inventoryRepository.save(inventory1)).thenReturn(inventory1);

        // Act
        Inventory updatedItem = inventoryService.updateItem(inventory1);

        // Assert
        assertNotNull(updatedItem, "Updated item should not be null");
        assertEquals(inventory1.getCode(), updatedItem.getCode(), "Codes should match");
        verify(inventoryRepository, times(1)).existsById(inventory1.getCode());
        verify(inventoryRepository, times(1)).save(inventory1);
    }

    @Test
    public void testUpdateItemNotFound() {
        // Arrange
        when(inventoryRepository.existsById(inventory1.getCode())).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, 
                () -> inventoryService.updateItem(inventory1),
                "Expected updateItem to throw RuntimeException when item not found");
        assertTrue(thrown.getMessage().contains("Item not found."), "Expected exception message to contain 'Item not found.'");
        verify(inventoryRepository, times(1)).existsById(inventory1.getCode());
        verify(inventoryRepository, never()).save(inventory1);
    }
}
