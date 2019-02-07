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
import com.witcher.rss.domain.model.User;
import com.witcher.rss.domain.services.admin.NewsAdminService;
import com.witcher.rss.domain.services.admin.NewsAdminServiceImpl;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class NewsAdminServiceTest {

    @TestConfiguration
    static class NewsAdminServiceTestConfiguration {

        @Bean
        public NewsAdminService newsAdminService() {
            return new NewsAdminServiceImpl();
        }
    }

    @Autowired
    private NewsAdminService newsAdminService;

    @MockBean
    private ThemeRepository themeRepository;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {

        final ThemeModel themeModel = new ThemeModel("test");
        themeModel.setId(1L);

        Mockito.when(themeRepository.save(any(ThemeModel.class))).thenReturn(themeModel);
        Mockito.when(themeRepository.findById(1L)).thenReturn(Optional.of(themeModel));
        Mockito.when(themeRepository.existsById(1L)).thenReturn(true);

        final NewsModel newsModel = new NewsModel("test", "test", LocalDateTime.now());
        newsModel.setId(1L);
        Mockito.when(newsRepository.save(any(NewsModel.class))).thenReturn(newsModel);
        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.of(newsModel));
        Mockito.when(newsRepository.existsById(1L)).thenReturn(true);

        final UserModel userModel = new UserModel("test", "test", "test");
        userModel.setId(1L);
        List<UserModel> models = Collections.singletonList(userModel);
        Mockito.when(userRepository.findAll()).thenReturn(models);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        Mockito.when(themeRepository.findAllById(Collections.singletonList(1L)))
                .thenReturn(Collections.singletonList(themeModel));
    }

    @Test
    public void testCreateTheme() {

        final Theme theme = newsAdminService.createTheme("test");

        Assert.assertNotNull(theme);
        Assert.assertEquals(theme.getId(), Long.valueOf(1L));
        Assert.assertEquals(theme.getTitle(), "test");
    }

    @Test
    public void testUpdateTitleTheme() throws ThemeNotFoundException {

        final Theme theme = newsAdminService.updateTitleTheme(1L, "new title");

        Assert.assertNotNull(theme);
        Assert.assertEquals(theme.getId(), Long.valueOf(1L));
        Assert.assertEquals(theme.getTitle(), "new title");
    }

    @Test
    public void testDeleteTheme() throws ThemeNotFoundException {

        newsAdminService.deleteTheme(1L);
    }

    @Test
    public void testCreateNews() {

        final News news = newsAdminService.createNews("test", "test", LocalDateTime.now());

        Assert.assertNotNull(news);
        Assert.assertEquals(news.getId(), Long.valueOf(1L));
        Assert.assertEquals(news.getLinkNews(), "test");
        Assert.assertEquals(news.getLinkIcon(), "test");
    }

    @Test
    public void testUpdateNews() throws NewsNotFoundException {

        final News news = newsAdminService.updateNews(1L, "new test", "new test", LocalDateTime.now());

        Assert.assertNotNull(news);
        Assert.assertEquals(news.getId(), Long.valueOf(1L));
        Assert.assertEquals(news.getLinkNews(), "new test");
        Assert.assertEquals(news.getLinkIcon(), "new test");
    }

    @Test
    public void testDeleteNews() throws NewsNotFoundException {

        newsAdminService.deleteNews(1L);
    }

    @Test
    public void testGetAllUsers() {

        final List<User> users = new ArrayList<>(newsAdminService.getAllUsers());

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.get(0).getId(), Long.valueOf(1L));
        Assert.assertEquals(users.get(0).getFullName(), "test");
        Assert.assertEquals(users.get(0).getLogin(), "test");
        Assert.assertEquals(users.get(0).getPassword(), "test");
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {

        final User user = newsAdminService.getUserById(1L);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), Long.valueOf(1L));
        Assert.assertEquals(user.getFullName(), "test");
        Assert.assertEquals(user.getLogin(), "test");
        Assert.assertEquals(user.getPassword(), "test");
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {

        newsAdminService.deleteUser(1L);
    }

    @Test
    public void testUpdateThemesReferences() throws NewsNotFoundException, ThemeNotFoundException {

        final List<Long> themeIds = Collections.singletonList(1L);

        final List<Long> resultList = new ArrayList<>(newsAdminService.updateThemesReferences(1L, themeIds));

        Assert.assertNotNull(resultList);
        Assert.assertEquals(themeIds.size(), 1);
        Assert.assertNotNull(themeIds.get(0));
    }
}
