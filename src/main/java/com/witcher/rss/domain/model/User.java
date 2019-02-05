package com.witcher.rss.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Builder
@Getter
public class User {

    private Long id;
    private String fullName;
    private String login;
    private String password;
}
