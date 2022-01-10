package com.github.prgrms.orders;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.security.JwtAuthentication;

import static com.github.prgrms.utils.ApiUtils.ApiResult;
import static com.github.prgrms.utils.ApiUtils.success;
import static com.github.prgrms.utils.ApiUtils.error;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;

@RestController
@RequestMapping("api/orders")
public class OrderRestController {
  private final OrderService orderService;

  public OrderRestController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping(path = "{id}")
  public ApiResult<OrdersDto> findById(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id) {
    return success(
      orderService.findById(id)
      .map(OrdersDto::new)
      .orElseThrow(() -> new NotFoundException("Could not found orders for " + id))
    );
  }

  @GetMapping
  public ApiResult<List<OrdersDto>> findAll(
    @AuthenticationPrincipal JwtAuthentication authentication,
    Pageable pageable) {
    System.out.println("페이지 "+pageable.getOffset()+" / "+pageable.getSize());
    return success(
      orderService.findAll(pageable).stream()
      .map(OrdersDto::new)
      .collect(toList())
    );
  }

  @PatchMapping(path = "{id}/accept")
  public ApiResult<Boolean> accept(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id
  ) {
    return success(
      orderService.accept(id)
    );
  }

  @PatchMapping(path = "{id}/shipping")
  public ApiResult<Boolean> shipping(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id
  ) {
    return success(
      orderService.shipping(id)
    );
  }

  @PatchMapping(path = "{id}/complete")
  public ApiResult<Boolean> complete(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id
  ) {
    return success(
      orderService.complete(id)
    );
  }

  @PatchMapping(path = "{id}/reject")
  public ApiResult<Boolean> reject(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable Long id,
    @RequestBody(required = false) HashMap<String, String> message
  ) {
    try {
      System.out.println("메시지 "+message.get("message"));
      return success(
        orderService.reject(id, message.get("message"))
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (ApiResult<Boolean>) error("거절 메시지 NULL", HttpStatus.BAD_REQUEST);
  }
}