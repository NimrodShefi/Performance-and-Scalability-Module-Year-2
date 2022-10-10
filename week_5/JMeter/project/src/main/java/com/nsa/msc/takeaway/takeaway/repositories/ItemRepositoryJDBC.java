package com.nsa.msc.takeaway.takeaway.repositories;

import com.nsa.msc.takeaway.takeaway.DTO.ItemDTO;
import com.nsa.msc.takeaway.takeaway.model.ItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepositoryJDBC implements ItemRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemRepositoryJDBC(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;
    }

    @Override
    public List<ItemDTO> findAll() {
        ItemRowMapper irm = new ItemRowMapper();
        return jdbcTemplate.query("select id, name, description, price from items", irm);
    }

    @Override
    public ItemDTO findById(Long id) {
        return null;
    }

    @Override
    public boolean addItem(ItemDTO itemDTO) {
        ItemRowMapper irm = new ItemRowMapper();
        jdbcTemplate.update("insert into items (name, description, price) values(? , ?, ?);",
                new Object[] {itemDTO.getName(),
                        itemDTO.getDescription(),
                        itemDTO.getPrice()} );
        return true;
    }

    @Override
    public int deleteItem(int id) {
        return jdbcTemplate.update("delete from items where id =(?);",
                new Object[] {id});
    }


}

