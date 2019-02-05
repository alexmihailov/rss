package com.witcher.rss.domain.util;

import com.witcher.rss.db.model.ThemeModel;
import com.witcher.rss.domain.model.Theme;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public final class ThemeConverter {

    private ThemeConverter() {
    }

    /**
     * Преобразовать объект БД в доменный объект.
     * @param model объект БД
     * @return доменный объект
     */
    public static Theme convertToThemeDomain(ThemeModel model) {
        return Theme.builder()
                .id(model.getId())
                .title(model.getTitle())
                .build();
    }

    /**
     * Преобразовать список объектов БД в список доменных объектов.
     * @param models список объектов БД
     * @return список доменных объектов
     */
    public static Collection<Theme> convertToListThemeDomain(Collection<ThemeModel> models) {
        return models.stream()
                .map(ThemeConverter::convertToThemeDomain)
                .collect(Collectors.toList());
    }
}
