package com.kim.springframework.transaction.dao;

import com.kim.springframework.transaction.Goods;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2022/1/18
 */
@Repository
@Data
public class GoodsDaoImpl implements GoodsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertWithException(Goods goods) {
        this.insert(goods);
        int i = 1/0;
    }

    @Override
    public void insert(Goods goods) {

        String sql="insert into t_goods (name,price,created_at,deleted_at)"+
                " values (?,?,?,?) ";
        jdbcTemplate.update(sql,goods.getName(),goods.getPrice(),goods.getCreatedAt(),goods.getDeletedAt());

    }

    @Override
    public List<Goods> findAll() {
        String sql="select id,name,price,created_at,deleted_at from t_goods";

        return jdbcTemplate.query(sql, new RowMapper<Goods>() {
            @Override
            public Goods mapRow(ResultSet rs, int rowNum) throws SQLException {
                Goods goods=new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setPrice(rs.getDouble("price"));
                goods.setCreatedAt(rs.getDate("created_at"));
                goods.setDeletedAt(rs.getDate("deleted_at"));
                return goods;
            }
        });
    }
}
