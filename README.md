# EbayCrawler
EbayCrawler is a Spring Boot web crawler.

EbayCrawler will crawl all the links in a specific URL and return tree like data structure that 
include url, http status, child links.

## Prerequisites

Before you begin, ensure you have met the following requirements:
* You have installed at least java 8.

## Installing EbayCrawler

To install EbayCrawler, follow these steps:

1. clone the project from github.
2. clean and install using maven.

## Using EbayCrawler

To use EbayCrawler, follow these steps:

1. run the jar.
2. send request with postman

## Assumptions

1. For Task 3 Improve the performance of your CrawlLinks API so it can support high crawlingDepth values (100 and more):
    I add in memory cache that will store all the visited links so if we visit it again we can retrieve the tree from the cache instead of crawl again and again.

2. For more scalable solution we can add redis db to store the visited links in cache db.

3. Another solution that require more time is to implement the crawler in multi-threads.