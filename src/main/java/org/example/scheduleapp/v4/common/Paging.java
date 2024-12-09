package org.example.scheduleapp.v4.common;

import lombok.Getter;

@Getter
public class Paging {
    private final int page;
    private final int size;
    private final int offset;

    public Paging(int page, int size) {
        this.page = Math.max(0, page);
        this.size = Math.max(1, size);
        this.offset = this.page * this.size;
    }
}