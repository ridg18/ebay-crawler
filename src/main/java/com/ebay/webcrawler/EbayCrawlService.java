package com.ebay.webcrawler;

import com.ebay.webcrawler.model.UrlInfo;
import com.ebay.webcrawler.model.UrlTree;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.cache.annotation.CacheResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created By: raga
 * Date: 05/06/2020
 * Time: 16:03
 */
@Service
@Log4j2
public class EbayCrawlService {

    @CacheResult(cacheName = "ebay-crawler")
    public UrlTree deepCrawl(final String url, final int depth, final List<String> processedUrls) throws IOException {
        int httpStatus;
        log.debug("Starting crawler for url {} for depth {}", url, depth);
        if (depth < 0) {
            log.info("Maximum depth reached, backing out for url {}", url);
            return null;
        } else {
            final List<String> updatedProcessedUrls = Optional.ofNullable(processedUrls).orElse(new ArrayList<>());
            if (!updatedProcessedUrls.contains(url)) {
                updatedProcessedUrls.add(url);
                httpStatus = Jsoup.connect(url).execute().statusCode();
                final UrlTree urlTree = new UrlTree(url, httpStatus);
                crawl(url).ifPresent(urlInfo -> {
                    urlTree.title(urlInfo.getTitle()).valid(true);
                    log.info("Found {} links on the web page: {}", urlInfo.getLinks().size(), url);
                    urlInfo.getLinks().forEach(link -> {
                        try {
                            urlTree.addNodesItem(deepCrawl(link.attr("abs:href"), depth - 1, updatedProcessedUrls));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
                return urlTree;
            } else {
                return null;
            }
        }

    }

    @CacheResult(cacheName = "ebay-crawler")
    public Optional<UrlInfo> crawl(final String url) {
        log.info("Fetching contents for url: {}", url);
        try {
            final Document doc = Jsoup.connect(url).get();
            final Elements links = doc.select("a[href]");
            final String title = doc.title();
            log.debug("Fetched title: {}, links[{}] for url: {}", title, links.nextAll(), url);
            return Optional.of(new UrlInfo(title, url, links));
        } catch (final IOException | IllegalArgumentException e) {
            log.error(String.format("couldn't get data from url  %s", url), e);
            return Optional.empty();
        }

    }

}
