package com.witcher.rss.domain.util;

import com.witcher.rss.db.model.UserModel;
import com.witcher.rss.domain.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public final class UserConverter {

    private UserConverter() {}

    /**
     * Преобразовать объект БД в доменный объект.
     * @param model объект БД
     * @return доменный объект
     */
    public static User convertToUserDomain(UserModel model) {
        return User.builder()
                .id(model.getId())
                .fullName(model.getFullName())
                .login(model.getLogin())
                .password(model.getPassword())
                .build();
    }

    /**
     * Преобразовать список объектов БД в список доменных объектов.
     * @param models список объектов БД
     * @return список доменных объектов
     */
    public static Collection<User> convertToUserListDomain(Collection<UserModel> models) {
        return models.stream().map(UserConverter::convertToUserDomain).collect(Collectors.toList());
    }
}
