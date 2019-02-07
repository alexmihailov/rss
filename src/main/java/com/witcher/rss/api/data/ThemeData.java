package com.witcher.rss.api.data;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Data
@Builder
@NoArgsConstructor
public class ThemeData {

    private Long id;
    private String title;

    public ThemeData(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
