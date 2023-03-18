package com.sanedge.myecommerce.auth.controllers;

import com.sanedge.myecommerce.dto.UpdateReviewStatusDto;
import com.sanedge.myecommerce.entity.Review;
import com.sanedge.myecommerce.exception.ItemNotFoundException;
import com.sanedge.myecommerce.mapper.ReviewMapper;
import com.sanedge.myecommerce.repository.ReviewRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.NoSuchElementException;
import static com.sanedge.myecommerce.utils.Constants.*;

import jakarta.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Review")
@RequestMapping(REVIEWS_ENDPOINT)
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    ReviewController(
            ReviewRepository reviewRepository,
            ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("test")
    public void Test() {
    }

    @GetMapping(params = { "_start", "_end", "_sort", "_order", "customer_id" })
    public ResponseEntity<List<Review>> getReviewForCustomer(
            @RequestParam(name = "_start") Integer start,
            @RequestParam(name = "_end") Integer end,
            @RequestParam(name = "_sort") String sort,
            @RequestParam(name = "_order") String order,
            @RequestParam(name = "customer_id", required = false) Long customer_id) {
        Integer take = end - start;

        List<Review> reviewsForProduct = reviewMapper.getPaginatedReviews(
                start,
                take,
                sort,
                order,
                null,
                null,
                customer_id,
                null);

        String reviewCount = reviewMapper.getReviewCount(
                null,
                null,
                customer_id,
                null);

        return ResponseEntity
                .ok()
                .header("X-Total-Count", reviewCount)
                .body(reviewsForProduct);
    }

    @GetMapping(params = { "_start", "_end", "_sort", "_order", "product_id" })
    public ResponseEntity<List<Review>> getReviewForProduct(
            @RequestParam(name = "_start") Integer start,
            @RequestParam(name = "_end") Integer end,
            @RequestParam(name = "_sort") String sort,
            @RequestParam(name = "_order") String order,
            @RequestParam(name = "product_id", required = false) Long product_id) {
        Integer take = end - start;

        List<Review> reviewsForProduct = reviewMapper.getPaginatedReviews(
                start,
                take,
                sort,
                order,
                null,
                product_id,
                null,
                null);

        String reviewCount = reviewMapper.getReviewCount(
                product_id,
                null,
                null,
                null);

        return ResponseEntity
                .ok()
                .header("X-Total-Count", reviewCount)
                .body(reviewsForProduct);
    }

    @GetMapping(params = { "_start", "_end", "_sort", "_order" })
    public ResponseEntity<List<Review>> getAllReviews(
            @RequestParam(name = "_start") Integer start,
            @RequestParam(name = "_end") Integer end,
            @RequestParam(name = "_sort") String sort,
            @RequestParam(name = "_order") String order,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "q", required = false) String queryByText) {
        Integer take = end - start;

        List<Review> allReviews = reviewMapper.getPaginatedReviews(
                start,
                take,
                sort,
                order,
                status,
                null,
                null,
                queryByText);

        String reviewCount = reviewMapper.getReviewCount(
                null,
                status,
                null,
                queryByText);

        return ResponseEntity
                .ok()
                .header("X-Total-Count", reviewCount)
                .body(allReviews);
    }

    @GetMapping("{id}")
    public ResponseEntity<Review> getById(@PathVariable("id") Long id) {
        Review review = reviewRepository
                .findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Review", id));

        return ResponseEntity.ok().body(review);
    }

    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<Review> update(
            @PathVariable("id") Long id,
            @RequestBody Review review) {
        Review updatedReview = reviewRepository
                .findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Review", id));

        if (review.getStatus() != null)
            updatedReview.setStatus(review.getStatus());
        if (review.getComment() != null)
            updatedReview.setComment(
                    review.getComment());

        return ResponseEntity.ok().body(updatedReview);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        reviewRepository.deleteById(id);

        return ResponseEntity.ok().body(true);
    }
}
