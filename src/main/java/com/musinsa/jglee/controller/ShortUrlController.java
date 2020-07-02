package com.musinsa.jglee.controller;

import com.musinsa.jglee.dao.ShortUrlDAO;
import com.musinsa.jglee.dto.request.CreateShortUrlRequestDTO;
import com.musinsa.jglee.dto.response.CreateShortUrlResponseDTO;
import com.musinsa.jglee.dto.response.GetShortUrlRedirectCountResponseDTO;
import com.musinsa.jglee.service.ShortUrlService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService, ShortUrlDAO shortUrlDAO) {
        this.shortUrlService = shortUrlService;
    }

    @RequestMapping(value = "/short-url", method = RequestMethod.POST)
    @ApiOperation(value="short url 생성", notes="1~zzzzzzzz 까지의 short url 을 생성한다.")
    public CreateShortUrlResponseDTO CreatedShortUrl(@RequestBody() CreateShortUrlRequestDTO createShortUrlRequestDTO) {
        return shortUrlService.GenerateShortUrl(createShortUrlRequestDTO.getUrl());
    }

    @RequestMapping(value = "/musinsa/{shorteningKey}", method = RequestMethod.GET)
    @ApiOperation(value="short url 리다이렉트", notes="DB 데이터를 shortening key 로 조회해서 리다이렉트 시켜준다.")
    public void RedirectShortUrl(HttpServletResponse response, @PathVariable("shorteningKey") String shorteningKey) throws IOException {
        String redirectUrl = shortUrlService.FindShortUrl(shorteningKey);
        response.sendRedirect(redirectUrl);
    }

    @RequestMapping(value = "/short-url/redirect-count", method = RequestMethod.GET)
    @ApiOperation(value="shorteningKey 로 연결 횟수 조회", notes="shorteningKey 로 조회된 횟수를 가져온다.")
    public GetShortUrlRedirectCountResponseDTO GetShortUrl(HttpServletResponse response, @RequestParam String shorteningKey) {
        return shortUrlService.FindShortUrlRedirectCount(shorteningKey);
    }
}
