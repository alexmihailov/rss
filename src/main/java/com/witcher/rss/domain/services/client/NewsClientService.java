package com.witcher.rss.domain.services.client;

import com.witcher.rss.domain.exceptions.NewsNotFoundException;
import com.witcher.rss.domain.exceptions.ThemeNotFoundException;
import com.witcher.rss.domain.exceptions.UserNotFoundException;
import com.witcher.rss.domain.model.News;
import com.witcher.rss.domain.model.Theme;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public interface NewsClientService {

    /**
     * Получить список всех тем.
     * @return список тем
     */
    Collection<Theme> getAllThemes();

    /**
     * Получить тему по ее id.
     * @param id идентификатор темы
     * @return Theme или Optional.empty если тема не найдена
     * @throws ThemeNotFoundException тема не найдена
     */
    Theme getThemeById(long id) throws ThemeNotFoundException;

    /**
     * Получить новость по ее идентификатору.
     * @param id идентификатор новости
     * @return News или Optional.empty если новость не найдена
     * @throws NewsNotFoundException новость не найдена
     */
    News getNewsById(long id) throws NewsNotFoundException;

    /**
     * Получить список новостей, которые подходят под указанные темы.
     * @param themeIds список идентификаторов тем
     * @return список новостей
     * @throws ThemeNotFoundException какая-то тема не найдена
     */
    Collection<News> getNewsByThemeIds(Collection<Long> themeIds) throws ThemeNotFoundException;

    /**
     * Задать темы по предпочтениям пользователя.
     * @param userId идентификатор пользователя
     * @param themeIds писок идентификаторов тем
     * @return список привязанных тем
     * @throws ThemeNotFoundException какая-то тема не найдена
     * @throws UserNotFoundException пользователь не найден
     */
    List<Long> updateUserPreferences(long userId, Collection<Long> themeIds) throws UserNotFoundException, ThemeNotFoundException;
}
