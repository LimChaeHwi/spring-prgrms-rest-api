package com.github.prgrms.orders;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.prgrms.security.JwtAuthentication;

import static com.github.prgrms.utils.ApiUtils.ApiResult;
import static com.github.prgrms.utils.ApiUtils.success;

import java.util.HashMap;

import static com.github.prgrms.utils.ApiUtils.error;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {
  private final ReviewService reviewService;

  public ReviewRestController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  // TODO review 메소드 구현이 필요합니다.
  @PostMapping(path = "{id}/review")
  public ApiResult<Boolean> review(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id,
    @RequestBody(required = false) HashMap<String, String> content
  ) {
    try {
      System.out.println("리뷰내용 "+content.get("content"));
      return success(
        reviewService.review(id, content.get("content"))
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (ApiResult<Boolean>) error("리뷰 내용 누락", HttpStatus.BAD_REQUEST);
  }
}