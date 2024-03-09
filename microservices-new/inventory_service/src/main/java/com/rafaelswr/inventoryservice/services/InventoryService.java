package com.rafaelswr.inventoryservice.services;

import com.rafaelswr.inventoryservice.dto.InventoryResponse;
import com.rafaelswr.inventoryservice.models.Inventory;
import com.rafaelswr.inventoryservice.repositories.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public InventoryResponse getInventoryBySkuCode(String skuCode, Integer quantity) throws Exception {
        Optional<Inventory> inv = inventoryRepository.findBySkuCode(skuCode);

        if(inv.isPresent()){
            return InventoryResponse.builder()
                    .skuCode(inv.get().getSkuCode())
                    .quantity(inv.get().getQuantity())
                    .isInStock((inv.get().getQuantity()-quantity)>=0)
                    .build();
        }else{
            throw new Exception("NOT FOUND PRODUCT");
        }
        /*
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
               throw new Exception("DONT EXCEED THE PRODUCT STOCK PLEASE");
            }
        } else {
            return InventoryResponse.builder().isInStock(false).build();
        }


         */
    }

    public List<InventoryResponse> getAll() {
        return inventoryRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.builder().skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity()).isInStock(inventory.getQuantity()>=0).build();
    }


    @Transactional
    public void getUpdateBySkuCode(String skuCode, Integer quantity) throws Exception {
        Optional<Inventory> inv = inventoryRepository.findBySkuCode(skuCode);
        if (inv.isPresent() && inv.get().getQuantity() - quantity >= 0) {
            int after = inv.get().getQuantity() - quantity;
            inv.get().setQuantity(after);
            log.info("UPDATED: " + inv.get().getQuantity());
        } else {
            throw new Exception("ERROR");
        }
    }

}
