package com.blog.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageEntity<T> {
    private long total;
    private List<T> data;

}
