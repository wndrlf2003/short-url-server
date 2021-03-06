package com.musinsa.jglee.service;

import com.musinsa.jglee.dao.ShortUrlDAO;
import com.musinsa.jglee.dto.response.CreateShortUrlResponseDTO;
import com.musinsa.jglee.dto.response.GetShortUrlRedirectCountResponseDTO;
import com.musinsa.jglee.schema.ShortUrl;
import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;

@Service
public class ShortUrlService {
    private final ShortUrlDAO shortUrlDAO;
    @Value("${redirect.base.url}")
    private String REDIRECT_BASE_URL;

    @Autowired
    public ShortUrlService(ShortUrlDAO shortUrlDAO) {
        this.shortUrlDAO = shortUrlDAO;
    }

    public CreateShortUrlResponseDTO generateShortUrl(String url) {
        CreateShortUrlResponseDTO createShortUrlResponseDTO = new CreateShortUrlResponseDTO();

        ShortUrl shortUrl = shortUrlDAO.findShortUrlByUrl(url);
        if (shortUrl == null) {
            try {
                shortUrl = this.saveShortUrl(url);
            } catch (Exception e) {
                createShortUrlResponseDTO.setCode(-1);
                createShortUrlResponseDTO.setShortUrl(null);
            }
        }

        createShortUrlResponseDTO.setCode(0);
        createShortUrlResponseDTO.setShortUrl(REDIRECT_BASE_URL + shortUrl.getShorteningKey());
        createShortUrlResponseDTO.setRedirectCount(shortUrl.getRedirectCount());
        return createShortUrlResponseDTO;
    }

    @Transactional
    public ShortUrl saveShortUrl(String url) throws Exception {
        ShortUrl shortUrl = new ShortUrl(url, null);
        ShortUrl resultShortUrl = shortUrlDAO.save(shortUrl);
        String shorteningKey = this.createShorteningKey(resultShortUrl.getId());
        if (shorteningKey.length() > 8) {
            throw new Exception();
        }
        resultShortUrl.setShorteningKey(shorteningKey);
        shortUrlDAO.save(resultShortUrl);
        return shortUrl;
    }

    public String createShorteningKey(long index) {
        Base62 base62 = Base62.createInstance();

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(index);

        String key = new String(base62.encode(buffer.array()));

        return key.replaceAll("^[0]+", "");
    }

    @Transactional()
    public String findShortUrl(String shorteningKey) {
        ShortUrl shortUrl = shortUrlDAO.findShortUrlByShorteningKey(shorteningKey);
        if (shortUrl == null) {
            return null;
        }
        // count 를 추가.
        shortUrl.setRedirectCount(shortUrl.getRedirectCount() + 1);
        shortUrl = shortUrlDAO.save(shortUrl);

        return shortUrl.getUrl();
    }

    public GetShortUrlRedirectCountResponseDTO findShortUrlRedirectCount(String shorteningKey) {
        GetShortUrlRedirectCountResponseDTO response = new GetShortUrlRedirectCountResponseDTO();
        ShortUrl shortUrl = shortUrlDAO.findShortUrlByShorteningKey(shorteningKey);
        if (shortUrl == null) {
            response.setCode(-1);
        } else {
            response.setRedirectCount(shortUrl.getRedirectCount());
        }

        return response;
    }
}
