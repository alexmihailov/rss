package com.witcher.rss.domain.services.admin;

import com.witcher.rss.db.model.NewsModel;
import com.witcher.rss.db.model.ThemeModel;
import com.witcher.rss.db.model.UserModel;
import com.witcher.rss.db.repository.NewsRepository;
import com.witcher.rss.db.repository.ThemeRepository;
import com.witcher.rss.db.repository.UserRepository;
import com.witcher.rss.domain.exceptions.NewsNotFoundException;
import com.witcher.rss.domain.exceptions.ThemeNotFoundException;
import com.witcher.rss.domain.exceptions.UserNotFoundException;
import com.witcher.rss.domain.model.News;
import com.witcher.rss.domain.model.Theme;
import com.witcher.rss.domain.model.User;
import com.witcher.rss.domain.util.NewsConverter;
import com.witcher.rss.domain.util.ThemeConverter;
import com.witcher.rss.domain.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Service
public class NewsAdminServiceImpl implements NewsAdminService {

    private ThemeRepository mThemeRepository;
    private NewsRepository mNewsRepository;
    private UserRepository mUserRepository;

    @Override
    public Theme createTheme(String title) {
        return ThemeConverter.convertToThemeDomain(
                mThemeRepository.save(new ThemeModel(title))
        );
    }

    @Override
    public Theme updateTitleTheme(long id, String title) throws ThemeNotFoundException {
        final ThemeModel themeModel = mThemeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException(id));
        themeModel.setTitle(title);

        return ThemeConverter.convertToThemeDomain(
                mThemeRepository.save(themeModel)
        );
    }

    @Override
    public void deleteTheme(long id) throws ThemeNotFoundException {
        if (!mThemeRepository.existsById(id)) {
            throw new ThemeNotFoundException(id);
        }
        mThemeRepository.deleteById(id);
    }

    @Override
    public News createNews(String linkNews, String linkIcon, LocalDateTime datePublication) {
        return NewsConverter.convertToNewsDomain(
                mNewsRepository.save(new NewsModel(linkNews, linkIcon, datePublication))
        );
    }

    @Override
    public News updateNews(long id, String linkNews, String linkIcon, LocalDateTime datePublication)
            throws NewsNotFoundException {

        final NewsModel newsModel = mNewsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));

        newsModel.setLinkNews(linkNews);
        newsModel.setLinkIcon(linkIcon);
        newsModel.setDatePublication(datePublication);

        return NewsConverter.convertToNewsDomain(
                mNewsRepository.save(newsModel)
        );
    }

    @Override
    public void deleteNews(long id) throws NewsNotFoundException {
        if (!mNewsRepository.existsById(id)) {
            throw new NewsNotFoundException(id);
        }
        mNewsRepository.deleteById(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return UserConverter.convertToUserListDomain(
                StreamSupport.stream(mUserRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public User getUserById(long id) throws UserNotFoundException {
        final UserModel userModel = mUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserConverter.convertToUserDomain(userModel);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        if (!mUserRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        mUserRepository.deleteById(id);
    }

    @Override
    public Collection<Long> updateThemesReferences(long newsId, Collection<Long> themeIds)
            throws NewsNotFoundException, ThemeNotFoundException {

        NewsModel newsModel = mNewsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(newsId));
        checkThemeExists(themeIds);

        final Set<ThemeModel> themeModels = StreamSupport.stream(
                mThemeRepository.findAllById(themeIds).spliterator(), false
        ).collect(Collectors.toSet());

        newsModel.setThemesForNews(themeModels);

        return mNewsRepository.save(newsModel).getThemesForNews().stream()
                .map(ThemeModel::getId)
                .collect(Collectors.toList());
    }

    /**
     * Проверка, есть ли указанные темы в БД, если нет выкинет исключение.
     * @param themeIds идентификаторы тем
     * @throws ThemeNotFoundException тема не найдена
     */
    private void checkThemeExists(Collection<Long> themeIds) throws ThemeNotFoundException {
        for(Long themeId : themeIds) {
            boolean existsTheme = mThemeRepository.existsById(themeId);
            if (!existsTheme) throw new ThemeNotFoundException(themeId);
        }
    }

    @Autowired
    public void setThemeRepository(ThemeRepository themeRepository) {
        mThemeRepository = themeRepository;
    }

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        mNewsRepository = newsRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        mUserRepository = userRepository;
    }
}
