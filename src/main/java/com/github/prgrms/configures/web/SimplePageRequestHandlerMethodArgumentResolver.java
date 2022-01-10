package com.github.prgrms.configures.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SimplePageRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String DEFAULT_OFFSET_PARAMETER = "offset";

  private static final String DEFAULT_SIZE_PARAMETER = "size";

  private static final long DEFAULT_OFFSET = 0;

  private static final int DEFAULT_SIZE = 5;

  private String offsetParameterName = DEFAULT_OFFSET_PARAMETER;

  private String sizeParameterName = DEFAULT_SIZE_PARAMETER;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Pageable.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
    MethodParameter methodParameter,
    ModelAndViewContainer mavContainer,
    NativeWebRequest webRequest,
    WebDataBinderFactory binderFactory
  ) {
    String offsetString = webRequest.getParameter(offsetParameterName);
    String sizeString = webRequest.getParameter(sizeParameterName);

    // TODO 구현이 필요 합니다.
    //throw new UnsupportedOperationException("SimplePageRequest 인스턴스를 리턴하도록 구현 필요");
    offsetString = NumberUtils.isParsable(offsetString) ? offsetString : "0";
    sizeString = NumberUtils.isParsable(sizeString) ? sizeString : "5";
    
    long offset = NumberUtils.toLong(offsetString);
    int size = NumberUtils.toInt(sizeString);

    offset = (offset < 0 || offset > Long.MAX_VALUE) ? 0 : offset;
    size = (size < 0 || size > 5) ? 5 : size;
    
    return new SimplePageRequest(offset, size);
  }

  public void setOffsetParameterName(String offsetParameterName) {
    this.offsetParameterName = offsetParameterName;
  }

  public void setSizeParameterName(String sizeParameterName) {
    this.sizeParameterName = sizeParameterName;
  }

}