package com.witcher.rss.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Getter
@Builder
public class News {

    private Long id;
    private String linkNews;
    private String linkIcon;
    private LocalDateTime datePublication;
}
