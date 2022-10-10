package com.nsa.msc.takeaway.takeaway.controller;

import com.nsa.msc.takeaway.takeaway.DTO.ItemDTO;
import com.nsa.msc.takeaway.takeaway.form.ItemForm;
import com.nsa.msc.takeaway.takeaway.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    private ItemRepository itemRepo;

    @Autowired
    public ItemController(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @RequestMapping(path = "public/menu")
    public String getAllItems(String name, Model model) {
        List<ItemDTO> menu = new ArrayList();
        menu = itemRepo.findAll();
        model.addAttribute("menu",menu);
        model.addAttribute("item", new ItemDTO(0,"test", "Test description", 100));
        return "menu";
    }

    @RequestMapping(path = "public/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }

    @RequestMapping(path = "public/menuHierarchical")
    public String getAllItemsHeirachacal(String name, Model model) {
        List<ItemDTO> menu = new ArrayList();
        menu = itemRepo.findAll();
        model.addAttribute("menu",menu);
        model.addAttribute("item", new ItemDTO(0,"test", "Test description", 100));
        return "hierarchicalMenu";
    }


    @RequestMapping(path = "/admin/deleteItem", method = RequestMethod.GET)
    public String getAllItemsForDelete(Model model) {
        List<ItemDTO> menu = new ArrayList();
        menu = itemRepo.findAll();
        model.addAttribute("menu",menu);
        model.addAttribute("item", new ItemDTO(0,"test", "Test description", 100));
        return "deleteItem";
    }

    @RequestMapping(path = "/admin/deleteItem/{id}", method = RequestMethod.POST)
    public RedirectView deleteItem(@PathVariable Optional<String> id) {
        int rowsAffected = 0;
        System.out.println("--IMC--ItemController.DeleteItem -- id = "+id);
        if(id.isPresent()) {
            rowsAffected = itemRepo.deleteItem(Integer.parseInt(id.get()) );
            System.out.println("--IMC--ItemController.DeleteItem -- rows affected = "+ rowsAffected);
        }
        return new RedirectView("/admin/deleteItem");
    }

    @RequestMapping(path = "/admin/addItem", method = RequestMethod.GET)
    public String addItemGet(String name, Model model) {
        return "addItem";
    }

    @RequestMapping(path = "/admin/addItem", method = RequestMethod.POST)
    public RedirectView addItemPost(ItemForm itemForm, BindingResult br, Model model) {
        System.out.println("ItemController.addItemPost --- in the add method");
        System.out.println("ItemController.addItemPost --- "+ itemForm);
        itemRepo.addItem(new ItemDTO(itemForm));

        List<ItemDTO> menu = new ArrayList();
        menu = itemRepo.findAll();
        System.out.println(menu);
        model.addAttribute("menu", menu);
        model.addAttribute("item", new ItemDTO(0,"test", "Test description", 100));

        return new RedirectView("/public/menu");
//        return new RedirectView("/admin/deleteItem");
    }

}
