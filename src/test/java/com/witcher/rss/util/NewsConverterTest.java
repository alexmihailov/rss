package com.witcher.rss.util;

import com.witcher.rss.db.model.NewsModel;
import com.witcher.rss.domain.model.News;
import com.witcher.rss.domain.util.NewsConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@RunWith(SpringRunner.class)
public class NewsConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final NewsModel model = new NewsModel("test", "test", LocalDateTime.now());
        model.setId(1L);

        final News news = NewsConverter.convertToNewsDomain(model);

        Assert.assertNotNull(news);
        Assert.assertEquals(news.getId(), model.getId());
        Assert.assertEquals(news.getLinkNews(), model.getLinkNews());
        Assert.assertEquals(news.getLinkIcon(), model.getLinkIcon());
        Assert.assertEquals(news.getDatePublication(), model.getDatePublication());
    }

    @Test
    public void testConvertListModelToListDomain() {

        List<NewsModel> models = new ArrayList<>();
        models.add(new NewsModel("test1", "test1", LocalDateTime.now()));
        models.add(new NewsModel("test2", "test2", LocalDateTime.now()));

        List<News> listNews = new ArrayList<>(NewsConverter.convertToNewsListDomain(models));

        Assert.assertNotNull(listNews);
        Assert.assertFalse(listNews.isEmpty());

        Assert.assertEquals(listNews.get(0).getLinkNews(), models.get(0).getLinkNews());
        Assert.assertEquals(listNews.get(0).getLinkIcon(), models.get(0).getLinkIcon());
        Assert.assertEquals(listNews.get(0).getDatePublication(), models.get(0).getDatePublication());

        Assert.assertEquals(listNews.get(1).getLinkNews(), models.get(1).getLinkNews());
        Assert.assertEquals(listNews.get(1).getLinkIcon(), models.get(1).getLinkIcon());
        Assert.assertEquals(listNews.get(1).getDatePublication(), models.get(1).getDatePublication());
    }
}
