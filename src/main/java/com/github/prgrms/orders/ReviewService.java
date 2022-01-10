package com.github.prgrms.orders;

import java.util.Optional;
import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;

  public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository) {
      this.reviewRepository = reviewRepository;
      this.orderRepository = orderRepository;
  }
  
  @Transactional(readOnly = true)
  public Optional<Review> findById(Long reviewId) {
    checkNotNull(reviewId, "reviewId must be provided");

    return reviewRepository.findById(reviewId);
  }

  @Transactional(readOnly = true)
  public Boolean review(Long orderId, String content) {
    if (orderRepository.findByIdAndState(orderId, OrderState.COMPLETED.getValue()) == null) {
      return false;
    }
    System.out.println("리뷰 생성 seq");
    int reviewSeq = reviewRepository.review(orderId, content);
    System.out.println("리뷰 seq"+reviewSeq);
    orderRepository.review(orderId, reviewSeq);
    System.out.println("종료");
    return true;
  }
}
