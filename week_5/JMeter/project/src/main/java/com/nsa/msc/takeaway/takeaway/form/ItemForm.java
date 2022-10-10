package com.nsa.msc.takeaway.takeaway.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemForm {
    int price;
    String description;
    String name;
}
