package com.witcher.rss.domain.services.client;

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
import com.witcher.rss.domain.util.NewsConverter;
import com.witcher.rss.domain.util.ThemeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Service
public class NewsClientServiceImpl implements NewsClientService {

    private ThemeRepository mThemeRepository;
    private NewsRepository mNewsRepository;
    private UserRepository mUserRepository;

    @Override
    public Collection<Theme> getAllThemes() {
        return StreamSupport.stream(mThemeRepository.findAll().spliterator(), false)
                .map(ThemeConverter::convertToThemeDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Theme getThemeById(long id) throws ThemeNotFoundException {
        return mThemeRepository.findById(id)
                .map(ThemeConverter::convertToThemeDomain)
                .orElseThrow(() -> new ThemeNotFoundException(id));
    }

    @Override
    public News getNewsById(long id) throws NewsNotFoundException {
        return mNewsRepository.findById(id)
                .map(NewsConverter::convertToNewsDomain)
                .orElseThrow(() -> new NewsNotFoundException(id));
    }

    @Override
    public Collection<News> getNewsByThemeIds(Collection<Long> themeIds) throws ThemeNotFoundException {
        checkThemeExists(themeIds);
        return StreamSupport.stream(mThemeRepository.findAllById(themeIds).spliterator(), false)
                .map(ThemeModel::getNews)
                .flatMap(Collection::stream)
                .distinct()
                .map(NewsConverter::convertToNewsDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> updateUserPreferences(long userId, Collection<Long> themeIds)
            throws UserNotFoundException, ThemeNotFoundException {

        Optional<UserModel> optUserModel = mUserRepository.findById(userId);
        final UserModel userModel = optUserModel.orElseThrow(() -> new UserNotFoundException(userId));

        checkThemeExists(themeIds);

        final Set<ThemeModel> themeModels = StreamSupport.stream(
                mThemeRepository.findAllById(themeIds).spliterator(), false
        ).collect(Collectors.toSet());

        userModel.setThemesForUser(themeModels);

        return mUserRepository.save(userModel).getThemesForUser().stream()
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
