package com.musinsa.jglee;

import com.musinsa.jglee.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class ShortUrlControllerTests {
	private final ShortUrlService shortUrlService;

	@Autowired
	public ShortUrlControllerTests(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	@Test
	void GenerateShorteningKeyLengthTest() {
		// Given
		long lastLengthNumber = 218340105584895L;
		long overLengthNumber = 218340105584896L;

		// When
		String lastLengthNumberKey = shortUrlService.CreateShorteningKey(lastLengthNumber);
		String overLengthNumberKey = shortUrlService.CreateShorteningKey(overLengthNumber);

		// Then
		assertEquals("base62 encode fail", lastLengthNumberKey, "zzzzzzzz");
		assertEquals("base62 encode size", lastLengthNumberKey.length(), 8);
		assertEquals("base62 encode fail", overLengthNumberKey, "100000000");
		assertEquals("base62 encode size", overLengthNumberKey.length(), 9);
	}

	@Test
	void AddShortUrlTest() {
		// Given

		// When

		// Then
	}

	@Test
	void GetShortUrlTest() {
		// Given

		// When

		// Then
	}

	@Test
	void AddExistShorteningKeyTest() {
		// Given

		// When

		// Then
	}
}
