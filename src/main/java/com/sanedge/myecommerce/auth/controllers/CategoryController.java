package com.sanedge.myecommerce.auth.controllers;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.myecommerce.entity.Category;
import com.sanedge.myecommerce.exception.ItemNotFoundException;
import com.sanedge.myecommerce.mapper.CategoryMapper;
import com.sanedge.myecommerce.repository.CategoryRepository;

import static com.sanedge.myecommerce.utils.Constants.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Categories")
@RequestMapping()
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    CategoryController(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(params = { "_start", "_end", "_sort", "_order" })
    public ResponseEntity<List<Category>> getAll(
            @RequestParam(name = "_start", required = false) String start,
            @RequestParam(name = "_end", required = false) Integer end,
            @RequestParam(name = "_sort", required = false) String sort,
            @RequestParam(name = "_order", required = false) String order) {
        List<Category> categories = categoryRepository.findAll();
        String categoryCount = categoryMapper.getCategoryCount();

        return ResponseEntity
                .ok()
                .header("X-Total-Count", categoryCount)
                .body(categories);
    }

    @GetMapping(params = "id")
    public ResponseEntity<List<Category>> getManyReference(
            @RequestParam(name = "id") Integer id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Category", id));

        return ResponseEntity.ok().body(Arrays.asList(category));
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getOne(@PathVariable("id") Integer id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Category", id));

        return ResponseEntity.ok().body(category);
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> update(
            @PathVariable("id") Integer id,
            @RequestBody Category item) {
        String newName = item.getName();
        Integer updateResult = categoryMapper.updateCategoryName(newName, id);

        return ResponseEntity.ok().body(updateResult > 0);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body(true);
    }
}
