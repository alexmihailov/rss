package com.witcher.rss.api.data;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Data
@Builder
@NoArgsConstructor
public class NewsData {

    private Long id;
    private String linkNews;
    private String linkIcon;
    private LocalDateTime datePublication;

    public NewsData(Long id, String linkNews, String linkIcon, LocalDateTime datePublication) {
        this.id = id;
        this.linkNews = linkNews;
        this.linkIcon = linkIcon;
        this.datePublication = datePublication;
    }
}
