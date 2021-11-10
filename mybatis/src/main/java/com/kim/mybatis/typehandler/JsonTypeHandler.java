package com.kim.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangjie
 * @description     mysql数据库json字段类型处理器
 * @date 2021/11/10
 */
public class JsonTypeHandler extends BaseTypeHandler<List<String>> {

    ObjectMapper mapper=new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {

        Map<String,Object> map=new HashMap<>();
        parameter.stream().forEach(s -> {
            map.put(s,null);
        });
        try {
            ps.setString(i,mapper.writeValueAsString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {

        return jsonToList(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return jsonToList(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return jsonToList(cs.getString(columnIndex));
    }


    private List<String> jsonToList(String json){
        try {
            Map<String,Object> map = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
            return map.keySet().stream().map(o -> {return o;}).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
