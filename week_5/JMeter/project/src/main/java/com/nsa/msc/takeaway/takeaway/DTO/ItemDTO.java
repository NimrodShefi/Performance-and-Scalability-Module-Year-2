package com.nsa.msc.takeaway.takeaway.DTO;


import com.nsa.msc.takeaway.takeaway.form.ItemForm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDTO {

    int id;
    String name;
    String description;
    int price;

    public ItemDTO(ItemForm itemForm) {
        this.name = itemForm.getName();
        this.description = itemForm.getDescription();
        this.price = itemForm.getPrice();
    }
}
