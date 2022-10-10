package com.nsa.msc.takeaway.takeaway.model;

import com.nsa.msc.takeaway.takeaway.DTO.ItemDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<ItemDTO> {
    @Override
    public ItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ItemDTO(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price")
        );
    }
}
