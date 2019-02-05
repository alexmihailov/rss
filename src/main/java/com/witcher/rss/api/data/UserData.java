package com.witcher.rss.api.data;

import lombok.Builder;
import lombok.Data;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Data
@Builder
public class UserData {

    private Long id;
    private String fullName;
    private String login;
    private String password;
}
