package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

public interface OrderRepository {
  List<Orders> findAll(Pageable pageable);
  
  Optional<Orders> findById(long id);

  Optional<Orders> findByIdAndState(long id, String state);

  int accept(long id);
  int shipping(long id);
  int complete(long id);
  int reject(long id, String rejectMsg);
  int review(long id, long reviewSeq);
  
}
