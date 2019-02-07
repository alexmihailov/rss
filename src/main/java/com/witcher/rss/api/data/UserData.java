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
public class UserData {

    private Long id;
    private String fullName;
    private String login;
    private String password;

    public UserData(Long id, String fullName, String login, String password) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }
}
