package com.github.prgrms.orders;
import java.util.Optional;

public interface ReviewRepository {
  Optional<Review> findById(long id);
  int review(long id, String content);
}
