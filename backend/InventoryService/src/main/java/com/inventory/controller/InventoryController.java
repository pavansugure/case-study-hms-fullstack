package com.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.model.Inventory;
import com.inventory.service.InventoryService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/inv")
public class InventoryController {

	private final InventoryService inventoryService; 
	
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService; 
	}
	
	@PostMapping("/add")
	public Inventory addItem(@Valid @RequestBody Inventory inv) {
		return inventoryService.addItem(inv);
	}
	
	@GetMapping("/{code}")
	public Inventory getItemByCode(@PathVariable Long code) {
		return inventoryService.getItemByCode(code);
	}
	
	@GetMapping
	public List<Inventory> getAllItems(){
		return inventoryService.getAllItems();
	}
	
	@DeleteMapping("/{code}")
	public void deleteItem(@PathVariable Long code) {
		inventoryService.deleteItem(code);
	}
	
	@PutMapping("/{code}")
	public Inventory updateItem(@Valid @RequestBody Inventory inv) {
		return inventoryService.updateItem(inv);
	}
}
