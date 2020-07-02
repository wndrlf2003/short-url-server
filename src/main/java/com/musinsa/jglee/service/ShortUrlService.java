package com.musinsa.jglee.service;

import com.musinsa.jglee.dao.ShortUrlDAO;
import com.musinsa.jglee.dto.response.CreateShortUrlResponseDTO;
import com.musinsa.jglee.schema.ShortUrl;
import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;

@Service
public class ShortUrlService {
    private final ShortUrlDAO shortUrlDAO;
    private final String REDIRECT_URL = "http://localhost:9009/musinsa/";

    @Autowired
    public ShortUrlService(ShortUrlDAO shortUrlDAO) {
        this.shortUrlDAO = shortUrlDAO;
    }

    public CreateShortUrlResponseDTO GenerateShortUrl(String url) {
        CreateShortUrlResponseDTO createShortUrlResponseDTO = new CreateShortUrlResponseDTO();

        ShortUrl shortUrl = shortUrlDAO.findShortUrlByUrl(url);
        if (shortUrl == null) {
            try {
                shortUrl = this.SaveShortUrl(url);
            } catch (Exception e) {
                createShortUrlResponseDTO.setCode(-1);
                createShortUrlResponseDTO.setShortUrl(null);
            }
        }

        createShortUrlResponseDTO.setCode(0);
        createShortUrlResponseDTO.setShortUrl(REDIRECT_URL + shortUrl.getShorteningKey());
        createShortUrlResponseDTO.setRedirectCount(shortUrl.getRedirectCount());
        return createShortUrlResponseDTO;
    }

    @Transactional
    public ShortUrl SaveShortUrl(String url) throws Exception {
        ShortUrl shortUrl = new ShortUrl(url, null);
        ShortUrl resultShortUrl = shortUrlDAO.save(shortUrl);
        String shorteningKey = this.CreateShorteningKey(resultShortUrl.getId());
        if (shorteningKey.length() > 8) {
            throw new Exception();
        }
        resultShortUrl.setShorteningKey(shorteningKey);
        shortUrlDAO.save(resultShortUrl);
        return shortUrl;
    }

    public String CreateShorteningKey(long index) {
        Base62 base62 = Base62.createInstance();

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(index);

        String key = new String(base62.encode(buffer.array()));

        return key.replaceAll("^[0]+", "");
    }

    @Transactional()
    public String FindShortUrl(String shorteningKey) {
        ShortUrl shortUrl = shortUrlDAO.findShortUrlByShorteningKey(shorteningKey);
        if (shortUrl == null) {
            return null;
        }
        // count 를 추가.
        shortUrl.setRedirectCount(shortUrl.getRedirectCount() + 1);
        shortUrl = shortUrlDAO.save(shortUrl);

        return shortUrl.getUrl();
    }
}
