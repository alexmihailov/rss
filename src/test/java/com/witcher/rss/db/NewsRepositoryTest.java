package com.witcher.rss.db;

import com.witcher.rss.db.model.NewsModel;
import com.witcher.rss.db.repository.NewsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void testSaveNews() {

        final String linkNews = "http://test-news-url.ru";
        final String linkIcon = "http://icon-news-url.ru";
        final LocalDateTime datePublication = LocalDateTime.now();

        final NewsModel newsModel = new NewsModel(linkNews, linkIcon, datePublication);

        final NewsModel saved = newsRepository.save(newsModel);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getLinkNews(), newsModel.getLinkNews());
        Assert.assertEquals(saved.getLinkIcon(), newsModel.getLinkIcon());
        Assert.assertEquals(saved.getDatePublication(), newsModel.getDatePublication());

        Optional<NewsModel> optSelectedValue = newsRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final NewsModel selectedValue = optSelectedValue.get();

        Assert.assertNotNull(selectedValue.getId());
        Assert.assertEquals(selectedValue.getId(), saved.getId());
        Assert.assertEquals(selectedValue.getLinkNews(), newsModel.getLinkNews());
        Assert.assertEquals(selectedValue.getLinkIcon(), newsModel.getLinkIcon());
        Assert.assertEquals(selectedValue.getDatePublication(), newsModel.getDatePublication());
    }

    @Test
    public void testUpdateNews() {

        final String linkNews = "http://test-news-url.ru";
        final String linkIcon = "http://icon-news-url.ru";
        final LocalDateTime datePublication = LocalDateTime.now();

        final NewsModel newsModel = new NewsModel(linkNews, linkIcon, datePublication);

        final NewsModel saved = newsRepository.save(newsModel);

        Optional<NewsModel> optSelectedValue = newsRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final NewsModel selectedValue = optSelectedValue.get();

        selectedValue.setLinkIcon("test_icon");
        selectedValue.setLinkNews("test_link");

        final NewsModel updated = newsRepository.save(newsModel);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getLinkIcon(), updated.getLinkIcon());
        Assert.assertEquals(selectedValue.getLinkNews(), updated.getLinkNews());
        Assert.assertEquals(newsModel.getDatePublication(), updated.getDatePublication());
    }

    @Test
    public void testDeleteNews() {

        final String linkNews = "http://test-news-url.ru";
        final String linkIcon = "http://icon-news-url.ru";
        final LocalDateTime datePublication = LocalDateTime.now();

        final NewsModel newsModel = new NewsModel(linkNews, linkIcon, datePublication);

        final NewsModel saved = newsRepository.save(newsModel);

        Optional<NewsModel> optSelectedValue = newsRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedValue.isPresent());

        final NewsModel selectedValue = optSelectedValue.get();

        newsRepository.delete(selectedValue);

        Optional<NewsModel> optSelectedValue2 = newsRepository.findById(saved.getId());

        Assert.assertFalse(optSelectedValue2.isPresent());
    }
}
