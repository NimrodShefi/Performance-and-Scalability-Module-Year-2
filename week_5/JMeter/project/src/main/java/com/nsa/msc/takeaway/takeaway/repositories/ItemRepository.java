package com.nsa.msc.takeaway.takeaway.repositories;

import com.nsa.msc.takeaway.takeaway.DTO.ItemDTO;

import java.util.List;

public interface ItemRepository {
    public List<ItemDTO> findAll();
    public ItemDTO findById(Long id);

    public boolean addItem(ItemDTO itemDTO);

    int deleteItem(int id);
}

