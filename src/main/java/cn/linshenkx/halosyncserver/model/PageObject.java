package cn.linshenkx.halosyncserver.model;

import lombok.Data;

import java.util.List;

@Data
public class PageObject<T> {
    private int pages;
    private int total;
    private int page;
    private int rpp;
    private List<T> content;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    private boolean isEmpty;
    private boolean hasContent;
}
