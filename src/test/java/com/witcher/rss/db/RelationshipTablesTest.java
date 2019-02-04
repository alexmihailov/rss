package com.witcher.rss.db;

import com.witcher.rss.db.model.NewsModel;
import com.witcher.rss.db.model.ThemeModel;
import com.witcher.rss.db.model.UserModel;
import com.witcher.rss.db.repository.NewsRepository;
import com.witcher.rss.db.repository.ThemeRepository;
import com.witcher.rss.db.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RelationshipTablesTest {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveRelationshipThemeNews() {

        final ThemeModel savedTheme = themeRepository.save(new ThemeModel("test theme"));

        final NewsModel newsModel = new NewsModel("test", "test", LocalDateTime.now());
        newsModel.addTheme(savedTheme);

        final NewsModel savedNews = newsRepository.save(newsModel);

        Assert.assertNotNull(savedNews.getThemesForNews());
        Assert.assertEquals(1, savedNews.getThemesForNews().size());
        Assert.assertTrue(savedNews.getThemesForNews().contains(savedTheme));
    }

    @Test
    public void testUpdateRelationshipThemeNews() {

        final ThemeModel savedTheme = themeRepository.save(new ThemeModel("test theme"));

        final NewsModel newsModel = new NewsModel("test", "test", LocalDateTime.now());
        newsModel.addTheme(savedTheme);

        final NewsModel savedNews = newsRepository.save(newsModel);
        final ThemeModel savedTheme2 = themeRepository.save(new ThemeModel("test2 theme"));
        savedNews.addTheme(savedTheme2);

        final NewsModel savedNews2 = newsRepository.save(savedNews);

        Assert.assertEquals(savedNews.getThemesForNews().size(), savedNews2.getThemesForNews().size());
        Assert.assertTrue(savedNews2.getThemesForNews().contains(savedTheme2));
        Assert.assertTrue(savedNews2.getThemesForNews().contains(savedTheme));
    }

    @Test
    public void testDeleteRelationshipThemeNews() {

        final ThemeModel savedTheme = themeRepository.save(new ThemeModel("test theme"));

        final NewsModel newsModel = new NewsModel("test", "test", LocalDateTime.now());
        newsModel.addTheme(savedTheme);

        final NewsModel savedNews = newsRepository.save(newsModel);
        savedNews.removeTheme(savedTheme);

        final NewsModel savedNews2 = newsRepository.save(savedNews);

        Assert.assertTrue(savedNews2.getThemesForNews().isEmpty());
    }

    @Test
    public void testSaveRelationshipThemeUser() {

        final ThemeModel savedTheme = themeRepository.save(new ThemeModel("test theme"));

        final UserModel userModel = new UserModel("test", "tes", "test");
        userModel.addTheme(savedTheme);

        final UserModel savedUser = userRepository.save(userModel);

        Assert.assertNotNull(savedUser.getThemesForUser());
        Assert.assertEquals(1, savedUser.getThemesForUser().size());
        Assert.assertTrue(savedUser.getThemesForUser().contains(savedTheme));
    }

    @Test
    public void testUpdateRelationshipThemeUser() {

        final ThemeModel savedTheme = themeRepository.save(new ThemeModel("test theme"));

        final UserModel userModel = new UserModel("test", "tes", "test");
        userModel.addTheme(savedTheme);

        final UserModel savedUser = userRepository.save(userModel);
        final ThemeModel savedTheme2 = themeRepository.save(new ThemeModel("test2 theme"));
        savedUser.addTheme(savedTheme2);

        final UserModel savedUser2 = userRepository.save(savedUser);

        Assert.assertEquals(2, savedUser2.getThemesForUser().size());
        Assert.assertTrue(savedUser2.getThemesForUser().contains(savedTheme2));
        Assert.assertTrue(savedUser2.getThemesForUser().contains(savedTheme));
    }

    @Test
    public void testDeleteRelationshipThemeUser() {

        final ThemeModel savedTheme = themeRepository.save(new ThemeModel("test theme"));

        final UserModel userModel = new UserModel("test", "tes", "test");
        userModel.addTheme(savedTheme);

        final UserModel savedUser = userRepository.save(userModel);

        savedUser.removeTheme(savedTheme);

        final UserModel savedUser2 = userRepository.save(savedUser);

        Assert.assertTrue(savedUser2.getThemesForUser().isEmpty());
    }
}
