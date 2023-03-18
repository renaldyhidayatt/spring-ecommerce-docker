package com.sanedge.myecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.myecommerce.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
