package com.musinsa.jglee;

import com.musinsa.jglee.service.ShortUrlService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

//@SpringBootTest
class ShortUrlTests {
	@Test
	void GenerateShorteningKeyLengthTest() {
		long lastLengthNumber = 218340105584895L;
		long overLengthNumber = 218340105584896L;
		ShortUrlService shortUrlService = mock(ShortUrlService.class);

		when(shortUrlService.createShorteningKey(lastLengthNumber)).thenReturn("zzzzzzzz");
		when(shortUrlService.createShorteningKey(overLengthNumber)).thenReturn("100000000");
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
