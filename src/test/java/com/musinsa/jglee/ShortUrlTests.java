package com.musinsa.jglee;

import com.musinsa.jglee.dao.ShortUrlDAO;
import com.musinsa.jglee.schema.ShortUrl;
import com.musinsa.jglee.service.ShortUrlService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

//@SpringBootTest
class ShortUrlTests {
	@Test
	void GenerateShorteningKeyLengthTest() {
		long lastLengthNumber = 218340105584895L;
		long overLengthNumber = 218340105584896L;
		ShortUrlService shortUrlService = mock(ShortUrlService.class);

		when(shortUrlService.CreateShorteningKey(lastLengthNumber)).thenReturn("zzzzzzzz");
		when(shortUrlService.CreateShorteningKey(overLengthNumber)).thenReturn("100000000");
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
