package com.sanedge.myecommerce.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.sanedge.myecommerce.entity.Review;

@Mapper
public interface ReviewMapper {
    public List<Review> getPaginatedReviews(
            @Param("start") Integer start,
            @Param("take") Integer take,
            @Param("sort") String sort,
            @Param("order") String order,
            @Param("status") String status,
            @Param("product_id") Long product_id,
            @Param("customer_id") Long customer_id,
            @Param("searchText") String searchText);

    public String getReviewCount(
            @Param("product_id") Long product_id,
            @Param("status") String status,
            @Param("customer_id") Long customer_id,
            @Param("searchText") String searchText);

    @Update("UPDATE review SET status = #{status} WHERE id = #{id}")
    public Review updateReviewStatus(
            @Param("id") Long id,
            @Param("status") String status);
}
