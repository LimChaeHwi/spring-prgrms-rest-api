package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final ReviewRepository reviewRepository;

  public OrderService(OrderRepository orderRepository, ReviewRepository reviewRepository) {
    this.orderRepository = orderRepository;
    this.reviewRepository = reviewRepository;
  }

  @Transactional(readOnly = true)
  public Optional<Orders> findById(Long orderId) {
    checkNotNull(orderId, "orderId must be provided");
    Optional<Orders> orders = orderRepository.findById(orderId);
    
    if (orders.get().getReviewSeq() > 0) {
      Optional<Review> review = reviewRepository.findById(orders.get().getReviewSeq());
      ReviewDto reviewDto = review.map(ReviewDto::new).orElseGet(null);
      orders.get().setReview(reviewDto);
    }
    
    return orders;
  }

  @Transactional(readOnly = true)
  public List<Orders> findAll(Pageable pageable) {
    List<Orders> orders = orderRepository.findAll(pageable);
    for(int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getReviewSeq() > 0) {
        Optional<Review> review = reviewRepository.findById(orders.get(i).getReviewSeq());
        ReviewDto reviewDto = review.map(ReviewDto::new).orElseGet(null);
        orders.get(i).setReview(reviewDto);
      }
    }
    return orders;
  }

  @Transactional(readOnly = true)
  public Boolean accept(Long orderId) {
     return (orderRepository.accept(orderId) > 0);
  }
  
  @Transactional(readOnly = true)
  public Boolean shipping(Long orderId) {
     return (orderRepository.shipping(orderId) > 0);
  }

  @Transactional(readOnly = true)
  public Boolean complete(Long orderId) {
     return (orderRepository.complete(orderId) > 0);
  }

  @Transactional(readOnly = true)
  public Boolean reject(Long orderId, String rejectMsg) {
     return (orderRepository.reject(orderId, rejectMsg) > 0);
  }
}
