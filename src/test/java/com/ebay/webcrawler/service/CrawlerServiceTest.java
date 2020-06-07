package com.ebay.webcrawler.service;

import com.ebay.webcrawler.EbayCrawlService;
import com.ebay.webcrawler.WebcrawlerApplication;
import com.ebay.webcrawler.model.UrlTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created By: raga
 * Date: 05/06/2020
 * Time: 18:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebcrawlerApplication.class)
@AutoConfigureMockMvc
public class CrawlerServiceTest {

    @Inject
    private EbayCrawlService ebayCrawlService;


    @Test
    public void testDeepCrawl() throws IOException {
        final UrlTree info = ebayCrawlService.deepCrawl("http://spring.io", 3, null);
        assertThat(info).isNotNull().satisfies(treeInfo -> {
            assertThat(treeInfo.getTitle()).contains("Spring");
            assertThat(treeInfo.getUrl()).contains("http://spring.io");
            assertThat(treeInfo.getNodes().size()).isGreaterThan(20);
        });
    }

}
