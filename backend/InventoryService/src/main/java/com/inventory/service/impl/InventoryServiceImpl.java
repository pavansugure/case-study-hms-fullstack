package com.inventory.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Inventory;
import com.inventory.repo.InventoryRepository;
import com.inventory.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory addItem(Inventory inv) {
        logger.info("Attempting to add item with details: {}", inv);
        Inventory addedItem = inventoryRepository.save(inv);
        logger.info("Item added successfully with ID: {}", addedItem.getCode());
        return addedItem;
    }

    @Override
    public Inventory getItemByCode(Long code) {
        logger.info("Fetching item with ID: {}", code);
        return inventoryRepository.findById(code).orElseThrow(() -> {
            logger.error("Item not found with ID: {}", code);
            return new ResourceNotFoundException("Item not found with code: " + code);
        });
    }

    @Override
    public List<Inventory> getAllItems() {
        logger.info("Fetching all items from the database.");
        List<Inventory> itemList = inventoryRepository.findAll();
        logger.info("Fetched {} items from the database.", itemList.size());
        return itemList;
    }

    @Override
    public void deleteItem(Long code) {
        logger.info("Attempting to delete item with ID: {}", code);
        if (!inventoryRepository.existsById(code)) {
            logger.error("Failed to delete item: Item not found with ID: {}", code);
            throw new ResourceNotFoundException("Item not found with code: " + code);
        }
        inventoryRepository.deleteById(code);
        logger.info("Item with ID {} deleted successfully.", code);
    }

	@Override
	public Inventory updateItem(Inventory inv) {
        if (!inventoryRepository.existsById(inv.getCode())) {
            throw new RuntimeException("Item not found.");
        }
        logger.debug("Updating item details in the database: {}", inv);
        return inventoryRepository.save(inv);
	}
}
