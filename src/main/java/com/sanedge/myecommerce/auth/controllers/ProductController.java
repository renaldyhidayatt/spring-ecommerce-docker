package com.sanedge.myecommerce.auth.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.persistence.EntityManager;
import static com.sanedge.myecommerce.utils.Constants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.myecommerce.entity.Category;
import com.sanedge.myecommerce.entity.Product;
import com.sanedge.myecommerce.exception.ItemNotFoundException;
import com.sanedge.myecommerce.mapper.ProductMapper;
import com.sanedge.myecommerce.repository.ProductRepository;

@RestController
@Tag(name = "products")
@RequestMapping(PRODUCTS_ENDPOINT)
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    public ProductController(
            ProductMapper productMapper,
            ProductRepository productRepository,
            EntityManager entityManager) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }

    @PutMapping("test")
    public void Test() {
    }

    @GetMapping(params = { "_start", "_end", "_sort", "_order" })
    public ResponseEntity<List<Product>> getAll(
            @RequestParam(name = "_end") Integer end,
            @RequestParam(name = "_sort") String sort,
            @RequestParam(name = "_order") String order,
            @RequestParam(name = "_start") Integer start,
            @RequestParam(name = "q", required = false) String queryByTitle,
            @RequestParam(name = "sales_gt", required = false) Integer sales_gt,
            @RequestParam(name = "stock_gt", required = false) Integer stock_gt,
            @RequestParam(name = "stock_lt", required = false) Integer stock_lt,
            @RequestParam(name = "sales_lte", required = false) Integer sales_lte,
            @RequestParam(name = "category_id", required = false) Long category_id) {
        Integer take = end - start;

        List<Product> paginatedProducts = productMapper.getPaginatedProducts(
                start,
                take,
                sort,
                order,
                category_id,
                sales_gt,
                sales_lte,
                stock_gt,
                stock_lt,
                queryByTitle);

        String productCount = productMapper.getProductCount(
                category_id,
                sales_gt,
                sales_lte,
                stock_gt,
                stock_lt,
                queryByTitle);

        return ResponseEntity
                .ok()
                .header("X-Total-Count", productCount)
                .body(paginatedProducts);
    }

    @GetMapping(params = "id")
    public ResponseEntity<List<Product>> getManyReference(
            @RequestParam("id") List<Long> ids) {
        List<Product> products = productMapper.getManyProducts(ids);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Product", id));

        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product item) {
        Integer categoryId = item.getCategory_id();

        Category category = entityManager.getReference(Category.class, categoryId);
        item.setCategory(category);

        Product createdProduct = productRepository.save(item);
        return ResponseEntity.ok().body(createdProduct);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(
            @PathVariable("id") Long id,
            @RequestBody Product item) {
        item.setId(id);
        Product savedProduct = productRepository.save(item);

        return ResponseEntity.ok().body(savedProduct);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().body(true);
    }
}
