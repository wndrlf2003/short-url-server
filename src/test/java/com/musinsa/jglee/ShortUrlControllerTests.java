package com.musinsa.jglee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.jglee.dto.response.CreateShortUrlResponseDTO;
import com.musinsa.jglee.dto.response.GetShortUrlRedirectCountResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShortUrlControllerTests {
	private final MockMvc mockMvc;

	@Autowired
	public ShortUrlControllerTests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test()
	void createdShortUrlCallTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/short-url").content("{\"url\":\"https://wndrlf2003.blog.me\"}").contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void getShortUrlRedirectCountCallTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/short-url/redirect-count?shorteningKey=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test // short url 생성 후 shorteningKey 로 카운트 조회 테스트
	void givenCreatedShortUrl_whenGetShortUrlRedirectCount_thenSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/short-url").content("{\"url\":\"https://wndrlf2003.blog.me\"}").contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/short-url/redirect-count?shorteningKey=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		GetShortUrlRedirectCountResponseDTO getShortUrlRedirectCountResponseDTO = mapper.readValue(mvcResult.getResponse().getContentAsString(), GetShortUrlRedirectCountResponseDTO.class);
		GetShortUrlRedirectCountResponseDTO correct = new GetShortUrlRedirectCountResponseDTO();
		correct.setCode(0);
		assertEquals(getShortUrlRedirectCountResponseDTO.getCode(), correct.getCode());
		assertEquals(getShortUrlRedirectCountResponseDTO.getRedirectCount(), correct.getRedirectCount());
	}

	@Test // 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답하는지 테스트.
	void givenCreateShortUrlMusinsa_whenCreateShortUrlMultiple_thenSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/short-url").content("{\"url\":\"https://store.musinsa.com/\"}").contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
		MvcResult firstShortUrlResult = mockMvc.perform(MockMvcRequestBuilders.post("/short-url").content("{\"url\":\"https://wndrlf2003.blog.me\"}").contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		MvcResult secondShortUrlResult = mockMvc.perform(MockMvcRequestBuilders.post("/short-url").content("{\"url\":\"https://wndrlf2003.blog.me\"}").contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		CreateShortUrlResponseDTO firstResponseDTO = mapper.readValue(firstShortUrlResult.getResponse().getContentAsString(), CreateShortUrlResponseDTO.class);
		CreateShortUrlResponseDTO secondResponseDTO = mapper.readValue(secondShortUrlResult.getResponse().getContentAsString(), CreateShortUrlResponseDTO.class);
		assertEquals(firstResponseDTO.getCode(), secondResponseDTO.getCode());
		assertEquals(firstResponseDTO.getShortUrl(), secondResponseDTO.getShortUrl());
		assertEquals(firstResponseDTO.getRedirectCount(), secondResponseDTO.getRedirectCount());
	}
}
