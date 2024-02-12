package com.rafaelswr.inventoryservice.services;

import com.rafaelswr.inventoryservice.dto.InventoryResponse;
import com.rafaelswr.inventoryservice.models.Inventory;
import com.rafaelswr.inventoryservice.repositories.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional()
    public InventoryResponse InStock(String skuCode) {

        Optional<Inventory> inv = inventoryRepository.findBySkuCode(skuCode);

        if (inv.isPresent()){
            if(inv.get().getQuantity()>0){
                return InventoryResponse.builder()
                        .isInStock(true)
                        .quantity(inv.get().getQuantity())
                        .skuCode(inv.get().getSkuCode())
                        .build();
            }
        }
        return InventoryResponse.builder().isInStock(false).build();
    }
    @Transactional
    public void quantityOps(InventoryResponse inventoryResponse) {
        log.info("INVENTORY RESPONSE INSTANCE:  " + inventoryResponse.toString());
        Optional<Inventory> inv = inventoryRepository.findBySkuCode(inventoryResponse.getSkuCode());
        inv.ifPresent(inventory -> inventory.setQuantity(inventoryResponse.getQuantity()));

 }

    @Transactional
    public InventoryResponse getInventoryBySkuCode(String skuCode, Integer quantity) {
        Optional<Inventory> inv = inventoryRepository.findBySkuCode(skuCode);

        if (inv.isPresent() && inv.get().getQuantity() > 0) {
            int totalStored = inv.get().getQuantity();
            int afterOp = totalStored - quantity;
            if (afterOp >= 0) {
                inv.get().setQuantity(afterOp);
                return InventoryResponse.builder()
                        .skuCode(inv.get().getSkuCode())
                        .quantity(inv.get().getQuantity())
                        .isInStock(true)
                        .build();
            } else {
                inv.get().setQuantity(0);
                return InventoryResponse.builder()
                        .skuCode(inv.get().getSkuCode())
                        .quantity(0)
                        .isInStock(false).build();
            }
        } else {
            return InventoryResponse.builder().isInStock(false).build();
        }

    }
}