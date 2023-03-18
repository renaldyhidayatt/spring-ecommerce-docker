package com.sanedge.myecommerce.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sanedge.myecommerce.dto.CommandDto;
import com.sanedge.myecommerce.entity.Command;

@Mapper
public interface CommandMapper {
    public List<Command> getManyCommands(List<Long> ids);

    public Command updateCommand(
            @Param("id") Long id,
            @Param("status") String status,
            @Param("returned") Boolean returned);

    public List<CommandDto> getPaginatedcommands(
            @Param("start") Integer start,
            @Param("take") Integer take,
            @Param("sort") String sort,
            @Param("order") String order,
            @Param("status") String status,
            @Param("date_gte") String date_gte,
            @Param("date_lte") String date_lte,
            @Param("customer_id") Long customer_id,
            @Param("total_gte") String total_gte,
            @Param("returned") Boolean returned,
            @Param("searchText") String searchText);

    public String getCommandCount(
            @Param("status") String status,
            @Param("date_gte") String date_gte,
            @Param("date_lte") String date_lte,
            @Param("customer_id") Long customer_id,
            @Param("total_gte") String total_gte,
            @Param("returned") Boolean returned,
            @Param("searchText") String searchText);
}