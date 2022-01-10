package com.github.prgrms.orders;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.springframework.beans.BeanUtils.copyProperties;

public class OrdersDto {
  private Long seq;
//   private Long userSeq;
  private Long productId;
  private ReviewDto review;
  
  private String state;
  private String requestMessage;
  private String rejectMessage;
  
  private LocalDateTime completedAt;
  private LocalDateTime rejectedAt;
  private LocalDateTime createAt;  

  public OrdersDto(Orders source) {
    copyProperties(source, this);
    this.productId = source.getProductSeq();
    this.requestMessage = source.getRequestMsg();
    this.rejectMessage = source.getRejectMsg();
  }

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
      this.seq = seq;
  }
  
  public Long getProductId() {
      return productId;
  }
  
  public void setProductId(Long productId) {
      this.productId = productId;
  }

  public ReviewDto getReview() {
      return review;
  }
  
  public void setReview(ReviewDto review) {
      this.review = review;
  }

  public String getState() {
      return state;
  }
  
  public void setState(String state) {
      this.state = state;
  }
  
  public String getRequestMessage() {
      return requestMessage;
  }
  
  public void setRequestMessage(String requestMessage) {
      this.requestMessage = requestMessage;
  }
  
  public String getRejectMessage() {
      return rejectMessage;
  }
  
  public void setRejectMessage(String rejectMessage) {
      this.rejectMessage = rejectMessage;
  }
  
  public LocalDateTime getCompletedAt() {
      return completedAt;
  }
  
  public void setCompletedAt(LocalDateTime completedAt) {
      this.completedAt = completedAt;
  }
  
  public LocalDateTime getRejectedAt() {
      return rejectedAt;
  }
  
  public void setRejectedAt(LocalDateTime rejectedAt) {
      this.rejectedAt = rejectedAt;
  }
  
  public LocalDateTime getCreateAt() {
      return createAt;
  }
  
  public void setCreateAt(LocalDateTime createAt) {
      this.createAt = createAt;
  }
  
  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("seq", seq)
        //.append("userSeq", userSeq)
        .append("productId", productId)
        .append("review", review)
        .append("state", state)
        .append("requestMessage", requestMessage)
        .append("rejectMessage", rejectMessage)
        .append("completedAt", completedAt)
        .append("rejectedAt", rejectedAt)
        .append("createAt", createAt)
        .toString();
  }
}
