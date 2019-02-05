package com.witcher.rss.api.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Data
@Builder
public class NewsData {

    private Long id;
    private String linkNews;
    private String linkIcon;
    private LocalDateTime datePublication;
}
