package com.inventory.service;

import java.util.List;

import com.inventory.model.Inventory;

public interface InventoryService {

	Inventory addItem(Inventory staff);
	Inventory getItemByCode(Long code);
	List<Inventory> getAllItems();
	void deleteItem(Long code);
	Inventory updateItem(Inventory inv);
}
