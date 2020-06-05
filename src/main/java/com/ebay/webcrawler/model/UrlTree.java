package com.ebay.webcrawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By: raga
 * Date: 05/06/2020
 * Time: 16:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlTree {

    private String url;

    private String title;

    private Boolean valid;

    private List<UrlTree> nodes;

    private int httpStatus;

    public UrlTree(final String url) {
        this.url = url;
        valid = false;
    }
    public UrlTree(final String url, int httpStatus) {
        this.url = url;
        this.httpStatus = httpStatus;
        valid = false;
    }

    public UrlTree addNodesItem(final UrlTree nodesItem) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        if (nodesItem != null) {
            nodes.add(nodesItem);
        }
        return this;
    }


    public UrlTree title(final String title) {
        this.title = title;
        return this;
    }

    public UrlTree valid(final Boolean valid) {
        this.valid = valid;
        return this;
    }
}
