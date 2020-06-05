package com.ebay.webcrawler.service;

import com.ebay.webcrawler.CrawlService;
import com.ebay.webcrawler.WebcrawlerApplication;
import com.ebay.webcrawler.model.UrlTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.test.context.junit4.SpringRunner;

import javax.cache.CacheManager;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.cache.interceptor.SimpleKey;

import java.io.IOException;

/**
 * Created By: raga
 * Date: 05/06/2020
 * Time: 18:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebcrawlerApplication.class)
public class CacheCrawlerServiceTest {

    @Value("#{cacheManager.getCache(\"ebay-crawler\")}")
    private Cache applicationCache;

    @Inject
    private CrawlService crawlerService;

    @Test
    public void testCacheOnDeepCrawl() throws IOException {
        final UrlTree info = crawlerService.deepCrawl("http://spring.io", 0, null);
        assertThat(info).isNotNull().satisfies(treeInfo -> {
            assertThat(treeInfo.getTitle()).contains("Spring");
            assertThat(treeInfo.getUrl()).contains("http://spring.io");
        });
        assertThat(applicationCache.getName()).isNotNull();
        assertThat(applicationCache.get(new SimpleKey("http://spring.io", 0, null))).isNotNull();
    }

}
