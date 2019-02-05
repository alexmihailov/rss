package com.witcher.rss.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Builder
@Getter
public class Theme {

    private Long id;
    private String title;
}
