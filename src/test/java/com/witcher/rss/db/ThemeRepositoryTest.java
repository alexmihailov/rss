package com.witcher.rss.db;

import com.witcher.rss.db.model.ThemeModel;
import com.witcher.rss.db.repository.ThemeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @Test
    public void testSaveTheme() {

        final ThemeModel themeModel = new ThemeModel("Test theme");

        final ThemeModel saved = themeRepository.save(themeModel);

        Assert.assertNotNull(saved);
        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getTitle(), themeModel.getTitle());

        Optional<ThemeModel> optSelectedTheme = themeRepository.findById(saved.getId());

        Assert.assertTrue(optSelectedTheme.isPresent());

        final ThemeModel selected = optSelectedTheme.get();

        Assert.assertEquals(saved.getId(), selected.getId());
        Assert.assertEquals(themeModel.getTitle(), selected.getTitle());
    }

    @Test
    public void testUpdateTheme() {

        final ThemeModel themeModel = new ThemeModel("Test theme");

        final ThemeModel saved = themeRepository.save(themeModel);

        saved.setTitle("new title");

        final ThemeModel updated = themeRepository.save(saved);

        Assert.assertEquals(updated.getId(), saved.getId());
        Assert.assertEquals(updated.getTitle(), saved.getTitle());
    }

    @Test
    public void testDeleteTheme() {

        final ThemeModel themeModel = new ThemeModel("Test theme");
        final ThemeModel saved = themeRepository.save(themeModel);

        themeRepository.delete(saved);

        Optional<ThemeModel> optSelectedTheme = themeRepository.findById(saved.getId());

        Assert.assertFalse(optSelectedTheme.isPresent());
    }
}
