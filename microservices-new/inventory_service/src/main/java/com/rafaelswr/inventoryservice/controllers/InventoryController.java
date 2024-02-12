package com.rafaelswr.inventoryservice.controllers;


import com.rafaelswr.inventoryservice.dto.InventoryResponse;
import com.rafaelswr.inventoryservice.services.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // http://localhost:8082/inventory/iphone-13
    //receive a list of skuCodes
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse isInStock(@PathVariable String skuCode){
        return inventoryService.InStock(skuCode);
    }

    @PutMapping("/ops")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse getInventoryInstance(@RequestParam String skuCode, @RequestParam Integer quantity){
        return inventoryService.getInventoryBySkuCode(skuCode, quantity);
    }

    @PutMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public void quantityOperation(@RequestBody InventoryResponse inventoryResponse){
        log.info("OBJETO RECEBIDO: "+ inventoryResponse);
        inventoryService.quantityOps(inventoryResponse);
    }

}
