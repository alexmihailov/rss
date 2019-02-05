package com.witcher.rss.api.data;

import lombok.Builder;
import lombok.Data;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Data
@Builder
public class ThemeData {

    private Long id;
    private String title;
}
