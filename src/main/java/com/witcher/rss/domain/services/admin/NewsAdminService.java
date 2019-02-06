package com.witcher.rss.domain.services.admin;

import com.witcher.rss.domain.exceptions.NewsNotFoundException;
import com.witcher.rss.domain.exceptions.ThemeNotFoundException;
import com.witcher.rss.domain.exceptions.UserNotFoundException;
import com.witcher.rss.domain.model.News;
import com.witcher.rss.domain.model.Theme;
import com.witcher.rss.domain.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public interface NewsAdminService {

    /**
     * Создать тему.
     * @param title заголовок темы
     * @return созданная тема
     */
    Theme createTheme(String title);

    /**
     * Обновить заголовок темы.
     * @param id идентификатор темы
     * @param title новый заголовок темы
     * @return обновленная тема
     */
    Theme updateTitleTheme(long id, String title) throws ThemeNotFoundException;

    /**
     * Удалить тему.
     * @param id идентификатор темы
     */
    void deleteTheme(long id) throws ThemeNotFoundException;

    /**
     * Создать новость.
     * @param linkNews ссылка на новость
     * @param linkIcon ссылка на иконку новости
     * @param datePublication дата публикации
     * @return созданная новость
     */
    News createNews(String linkNews, String linkIcon, LocalDateTime datePublication);

    /**
     * Обновить данные новости.
     * @param id идентификатор новости
     * @param linkNews ссылка на новость
     * @param linkIcon ссылка на иконку новости
     * @param datePublication дата публикации
     * @return обновленная новость
     */
    News updateNews(long id, String linkNews, String linkIcon, LocalDateTime datePublication) throws NewsNotFoundException;

    /**
     * Удалить новость.
     * @param id идентификатор новости
     */
    void deleteNews(long id) throws NewsNotFoundException;

    /**
     * Получить список всех пользователей.
     * @return список юзеров
     */
    Collection<User> getAllUsers();

    /**
     * Получить пользователя по его идентификатору.
     * @param id идентификатор пользователя
     * @return пользователь
     */
    User getUserById(long id) throws UserNotFoundException;

    /**
     * Удалить пользователя.
     * @param id идентификатор пользователя
     */
    void deleteUser(long id) throws UserNotFoundException;

    /**
     * Связать новость с темами.
     * @param newsId идентификатор новости
     * @param themeIds идентификаторы тем
     * @return список связанных с новостью тем
     */
    Collection<Long> updateThemesReferences(long newsId, Collection<Long> themeIds) throws NewsNotFoundException, ThemeNotFoundException;
}
