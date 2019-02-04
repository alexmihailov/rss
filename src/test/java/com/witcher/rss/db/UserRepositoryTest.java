package com.witcher.rss.db;

import com.witcher.rss.db.model.UserModel;
import com.witcher.rss.db.repository.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {

        final UserModel userModel = new UserModel("Test full name", "test login", "test pass");
        final UserModel saved = userRepository.save(userModel);

        Assert.assertNotNull(saved);
        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getFullName(), userModel.getFullName());
        Assert.assertEquals(saved.getLogin(), userModel.getLogin());
        Assert.assertEquals(saved.getPassword(), userModel.getPassword());

        Optional<UserModel> optSelected = userRepository.findById(saved.getId());

        Assert.assertTrue(optSelected.isPresent());

        final UserModel selected = optSelected.get();

        Assert.assertEquals(saved.getId(), selected.getId());
        Assert.assertEquals(saved.getFullName(), selected.getFullName());
        Assert.assertEquals(saved.getLogin(), selected.getLogin());
        Assert.assertEquals(saved.getPassword(), selected.getPassword());
    }

    @Test
    public void testUpdateUser() {

        final UserModel userModel = new UserModel("Test full name", "test login", "test pass");
        final UserModel saved = userRepository.save(userModel);

        saved.setFullName("new full name");
        saved.setLogin("new login");

        final UserModel saved2 = userRepository.save(saved);

        Assert.assertEquals(saved.getId(), saved2.getId());
        Assert.assertEquals(saved.getFullName(), saved2.getFullName());
        Assert.assertEquals(saved.getLogin(), saved2.getLogin());
        Assert.assertEquals(saved.getPassword(), saved2.getPassword());
    }

    @Test
    public void testDeleteUser() {

        final UserModel userModel = new UserModel("Test full name", "test login", "test pass");
        final UserModel saved = userRepository.save(userModel);

        userRepository.delete(saved);

        Optional<UserModel> optSelected = userRepository.findById(saved.getId());

        Assert.assertFalse(optSelected.isPresent());
    }
}
