package com.witcher.rss.util;

import com.witcher.rss.db.model.ThemeModel;
import com.witcher.rss.domain.model.Theme;
import com.witcher.rss.domain.util.ThemeConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@RunWith(SpringRunner.class)
public class ThemeConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final ThemeModel model = new ThemeModel("test");
        model.setId(1L);

        final Theme theme = ThemeConverter.convertToThemeDomain(model);

        Assert.assertNotNull(theme);
        Assert.assertEquals(theme.getId(), model.getId());
        Assert.assertEquals(theme.getTitle(), model.getTitle());
    }

    @Test
    public void testConvertListModelToListDomain() {

        final List<ThemeModel> models = new ArrayList<>();
        models.add(new ThemeModel("test1"));
        models.add(new ThemeModel("test2"));

        final List<Theme> themeList = new ArrayList<>(ThemeConverter.convertToListThemeDomain(models));

        Assert.assertNotNull(themeList);
        Assert.assertFalse(themeList.isEmpty());

        Assert.assertEquals(themeList.get(0).getTitle(), models.get(0).getTitle());
        Assert.assertEquals(themeList.get(1).getTitle(), models.get(1).getTitle());
    }
}
