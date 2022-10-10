package com.nsa.msc.takeaway.takeaway.controller;

import com.nsa.msc.takeaway.takeaway.DTO.ItemDTO;
import com.nsa.msc.takeaway.takeaway.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ItemRestController {

     private ItemRepository itemRepo;

     @Autowired
     public ItemRestController(ItemRepository itemRepo) {
         this.itemRepo = itemRepo;
     }

     @RequestMapping(path = "/menu", method = RequestMethod.GET)
     public @ResponseBody List<ItemDTO> getAllItems(String name, Model model) {
         List<ItemDTO> menu = new ArrayList();
         menu = itemRepo.findAll();
         model.addAttribute("menu",menu);
         model.addAttribute("item", new ItemDTO(0,"test", "Test description", 100));
         return menu;
     }
}
