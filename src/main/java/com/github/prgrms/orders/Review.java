package com.github.prgrms.orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Review {
  private final Long seq;
  private final Long userSeq;
  private final Long productSeq;
  private String content;
  private LocalDateTime createAt;

  public Review(String content) {
    this(null, null, null, content, null);
  }

  public Review(Long seq, Long userSeq, Long productSeq, String content, LocalDateTime createAt) {
    this.seq = seq;
    this.userSeq = userSeq;
    this.productSeq = productSeq;
    this.content = content;
    this.createAt = createAt;
  }

  public Long getSeq() {
      return seq;
  }
  
  public Long getUserSeq() {
      return userSeq;
  }
  
  public Long getProductSeq() {
      return productSeq;
  }
  
  public String getContent() {
      return content;
  }
  
  public void setContent(String content) {
      this.content = content;
  }
  
  public LocalDateTime getCreateAt() {
      return createAt;
  }
  
  public void setCreateAt(LocalDateTime createAt) {
      this.createAt = createAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Review review = (Review) o;
    return Objects.equals(seq, review.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }

  @Override
  public String toString() {
      return "Review [content=" + content + ", createAt=" + createAt + ", productSeq=" + productSeq + ", seq=" + seq
              + ", userSeq=" + userSeq + "]";
  }

  
  static public class Builder {
    private Long seq;
    private Long userSeq;
    private Long productSeq;
  
    private String content;
    private LocalDateTime createAt;
    
    public Builder() {/*empty*/}

    public Builder(Review review) {
        this.seq = review.seq;
        this.userSeq = review.userSeq;
        this.productSeq = review.productSeq;
        this.content = review.content;
        this.createAt = review.createAt;
    }

    public Builder seq(Long seq) {
      this.seq = seq;
      return this;
    }
    public Builder userSeq(Long userSeq) {
      this.userSeq = userSeq;
      return this;
    }
    public Builder productSeq(Long productSeq) {
      this.productSeq = productSeq;
      return this;
    }
    public Builder content(String content) {
      this.content = content;
      return this;
    }
    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }
    
    public Review build() {
      return new Review(
        seq,
        userSeq,
        productSeq,
        content,
        createAt
      );
    }
  }
  
}
