package com.ebay.webcrawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.select.Elements;

/**
 * Created By: raga
 * Date: 05/06/2020
 * Time: 16:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfo {

    private String title;

    private String url;

    private Elements links;
}
