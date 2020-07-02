package com.musinsa.jglee.dao;

import com.musinsa.jglee.schema.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlDAO extends JpaRepository<ShortUrl, Long> {
    boolean existsShortUrlByShorteningKey(String ShorteningKey);
    boolean existsShortUrlByUrl(String url);
    ShortUrl findShortUrlByUrl(String url);
    ShortUrl findShortUrlByShorteningKey(String ShorteningKey);
}
