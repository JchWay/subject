package com.springboot.attendsys.mapper;

import com.springboot.attendsys.model.Selected;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SelectedMapper {
    @Select("select * from t_selected where c_id = #{cid} and u_id = #{uid}")
    Selected getByIds(@Param("cid")int cid, @Param("uid") int uid);
}
