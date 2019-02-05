package com.witcher.rss.domain.util;

import com.witcher.rss.db.model.NewsModel;
import com.witcher.rss.domain.model.News;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public final class NewsConverter {

    private NewsConverter() {
    }

    /**
     * Конвертация объекта БД в доменный объект.
     * @param model объект БД
     * @return доменный объект
     */
    public static News convertToNewsDomain(NewsModel model) {
        return News.builder()
                .id(model.getId())
                .linkNews(model.getLinkNews())
                .linkIcon(model.getLinkIcon())
                .datePublication(model.getDatePublication())
                .build();
    }

    /**
     * Конвертация списока объектов БД в список доменных объектов
     * @param models список объектов БД
     * @return список доменных объектов
     */
    public static Collection<News> convertToNewsListDomain(Collection<NewsModel> models) {
        return models.stream()
                .map(NewsConverter::convertToNewsDomain)
                .collect(Collectors.toList());
    }
}
