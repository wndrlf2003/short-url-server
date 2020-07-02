package com.musinsa.jglee;

import com.musinsa.jglee.dao.ShortUrlDAO;
import com.musinsa.jglee.dto.response.CreateShortUrlResponseDTO;
import com.musinsa.jglee.schema.ShortUrl;
import com.musinsa.jglee.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ShortUrlTests {
	private final ShortUrlService shortUrlService;
	private final ShortUrlDAO shortUrlDAO;

	@Value("${redirect.base.url}")
	private String REDIRECT_BASE_URL;

	@Autowired
	public ShortUrlTests(ShortUrlService shortUrlService, ShortUrlDAO shortUrlDAO) {
		this.shortUrlService = shortUrlService;
		this.shortUrlDAO = shortUrlDAO;
	}

	@Test // short url 의 최대 길이가 8자리가 되는 숫자 범위를 확인.
	void givenSettingLong_whenCreateShorteningKey_thenCheckShorteningKeyLength() {
		long lastLengthNumber = 218340105584895L;
		long overLengthNumber = 218340105584896L;
		ShortUrlService shortUrlService = mock(ShortUrlService.class);

		when(shortUrlService.createShorteningKey(lastLengthNumber)).thenReturn("zzzzzzzz");
		when(shortUrlService.createShorteningKey(overLengthNumber)).thenReturn("100000000");
	}

	@Test // short url db 추가, shortening key 조회 테스트.
	void givenShortUrl_whenInsertShortUrlAndSelectShortUrlByShorteningKey_thenEquals() throws Exception {
		ShortUrl insertShortUrlResult = shortUrlService.saveShortUrl("http://test.com");
		ShortUrl selectShortUrlResult = shortUrlDAO.findShortUrlByShorteningKey(insertShortUrlResult.getShorteningKey());
		assertEquals(insertShortUrlResult.getId(), selectShortUrlResult.getId());
	}

	@Test // generateShortUrl 메소드 동작 테스트
	void givenShortUrl_whenGenerateShortUrlAndSelectShortUrlByUrl_thenEquals() throws Exception {
		CreateShortUrlResponseDTO generateShortUrlResult = shortUrlService.generateShortUrl("test");
		List<ShortUrl> selectShortUrlResults = shortUrlDAO.findAll();
		String shorteningKey = shortUrlService.createShorteningKey(selectShortUrlResults.get(0).getId());

		assertEquals(generateShortUrlResult.getShortUrl(), REDIRECT_BASE_URL + shorteningKey);
	}
}
