package com.witcher.rss.service;

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
import com.witcher.rss.domain.services.client.NewsClientService;
import com.witcher.rss.domain.services.client.NewsClientServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@RunWith(SpringRunner.class)
public class NewsClientServiceTest {

    @TestConfiguration
    static class NewsClientServiceTestConfiguration {

        @Bean
        public NewsClientService newsClientService() {
            return new NewsClientServiceImpl();
        }
    }

    @Autowired
    private NewsClientService newsClientService;

    @MockBean
    private ThemeRepository themeRepository;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        List<ThemeModel> models = new ArrayList<>();
        models.add(new ThemeModel("test"));

        Mockito.when(themeRepository.findAll())
                .thenReturn(models);
        Mockito.when(themeRepository.findById(1L))
                .thenReturn(
                        Optional.of(new ThemeModel("test"))
                );
        Mockito.when(newsRepository.findById(1L))
                .thenReturn(
                        Optional.of(new NewsModel("link", "icon", LocalDateTime.now()))
                );

        Set<NewsModel> newsModels = new HashSet<>();
        newsModels.add(new NewsModel("test1", "test1", LocalDateTime.now()));
        newsModels.add(new NewsModel("test2", "test2", LocalDateTime.now()));

        final ThemeModel theme1 = new ThemeModel("test theme1");
        theme1.setId(1L);
        theme1.setNews(newsModels);

        final ThemeModel theme2 = new ThemeModel("test theme2");
        theme2.setId(2L);
        theme2.setNews(newsModels);

        final ThemeModel theme3 = new ThemeModel("test theme3");
        theme3.setId(3L);
        theme3.setNews(newsModels);

        Mockito.when(themeRepository.existsById(1L)).thenReturn(true);
        Mockito.when(themeRepository.existsById(2L)).thenReturn(true);
        Mockito.when(themeRepository.existsById(3L)).thenReturn(true);
        Mockito.when(themeRepository.findAllById(Arrays.asList(1L, 2L, 3L)))
                .thenReturn(
                        Arrays.asList(theme1, theme2, theme3)
                );

        UserModel userModel = new UserModel("test", "test", "test");
        userModel.setId(1L);
        userModel.setThemesForUser(Stream.of(theme1, theme2, theme3).collect(Collectors.toSet()));
        Mockito.when(userRepository.findById(1L)).thenReturn(
                Optional.of(userModel)
        );

        Mockito.when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
    }

    @Test
    public void testGetAllThemes() {

        final List<Theme> themes = new ArrayList<>(newsClientService.getAllThemes());

        Assert.assertNotNull(themes);
        Assert.assertEquals(1, themes.size());
        Assert.assertEquals(themes.get(0).getTitle(), "test");
    }

    @Test
    public void testGetThemeById() throws ThemeNotFoundException {

        Theme theme = newsClientService.getThemeById(1L);

        Assert.assertNotNull(theme);
        Assert.assertEquals(theme.getTitle(), "test");
    }

    @Test
    public void testGetNewsById() throws NewsNotFoundException {

        News news = newsClientService.getNewsById(1L);

        Assert.assertNotNull(news);
        Assert.assertEquals(news.getLinkNews(), "link");
        Assert.assertEquals(news.getLinkIcon(), "icon");
    }

    @Test
    public void testGetNewsByThemeIds() throws ThemeNotFoundException {

        List<News> news = new ArrayList<>(newsClientService.getNewsByThemeIds(Arrays.asList(1L, 2L, 3L)));

        Assert.assertNotNull(news);
        Assert.assertEquals(2, news.size());
    }

    @Test
    public void testUpdateUserPreferences() throws ThemeNotFoundException, UserNotFoundException {

        List<Long> savedIds = newsClientService.updateUserPreferences(1L, Arrays.asList(1L, 2L, 3L));

        Assert.assertNotNull(savedIds);
        Assert.assertEquals(3, savedIds.size());
    }
}
