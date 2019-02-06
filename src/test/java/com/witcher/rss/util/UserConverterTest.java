package com.witcher.rss.util;

import com.witcher.rss.db.model.UserModel;
import com.witcher.rss.domain.model.User;
import com.witcher.rss.domain.util.UserConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class UserConverterTest {

    @Test
    public void testConvertModelToDomain() {

        final UserModel userModel = new UserModel("test", "test", "test");
        userModel.setId(1L);

        final User user = UserConverter.convertToUserDomain(userModel);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), userModel.getId());
        Assert.assertEquals(user.getFullName(), userModel.getFullName());
        Assert.assertEquals(user.getLogin(), userModel.getLogin());
        Assert.assertEquals(user.getPassword(), userModel.getPassword());
    }

    @Test
    public void testConvertListModelToListDomain() {

        List<UserModel> modelList = new ArrayList<>();
        modelList.add(new UserModel("test1", "test1", "test1"));
        modelList.add(new UserModel("test2", "test2", "test2"));

        List<User> users = new ArrayList<>(UserConverter.convertToUserListDomain(modelList));

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 2);
    }
}
