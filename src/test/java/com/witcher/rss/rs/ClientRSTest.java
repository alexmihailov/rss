package com.witcher.rss.rs;

import com.witcher.rss.api.data.ResultResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientRSTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllThemes() {

        ResponseEntity<ResultResponse> entity = this.restTemplate.getForEntity("/clients/themes",
                ResultResponse.class);

        Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(entity.getBody());
        Assert.assertNotNull(entity.getBody().getMeta());
        Assert.assertNotNull(entity.getBody().getData());
        Assert.assertEquals(entity.getBody().getMeta().getStatus(), ResultResponse.Status.OK);
    }

    @Test
    public void testGetThemeByIdWhenNotFound() {

        ResponseEntity<ResultResponse> entity = this.restTemplate.getForEntity("/clients/theme/1",
                ResultResponse.class);

        Assert.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertNotNull(entity.getBody());
        Assert.assertNotNull(entity.getBody().getMeta());
        Assert.assertNull(entity.getBody().getData());
        Assert.assertEquals(entity.getBody().getMeta().getStatus(), ResultResponse.Status.ERROR);
    }

    @Test
    public void testGetNewsByIdWhenNotFound() {

        ResponseEntity<ResultResponse> entity = this.restTemplate.getForEntity("/clients/news/1",
                ResultResponse.class);

        Assert.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertNotNull(entity.getBody());
        Assert.assertNotNull(entity.getBody().getMeta());
        Assert.assertNull(entity.getBody().getData());
        Assert.assertEquals(entity.getBody().getMeta().getStatus(), ResultResponse.Status.ERROR);
    }

    @Test
    public void testGetNewsByThemeIdsWhenNotFound() {

        ResponseEntity<ResultResponse> entity = this.restTemplate.getForEntity("/clients//news?ids=1&ids=2",
                ResultResponse.class);

        Assert.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertNotNull(entity.getBody());
        Assert.assertNotNull(entity.getBody().getMeta());
        Assert.assertNull(entity.getBody().getData());
        Assert.assertEquals(entity.getBody().getMeta().getStatus(), ResultResponse.Status.ERROR);
    }

    @Test
    public void testUpdateUserPreferencesWhereNotFound() {

        HttpEntity<Collection<Long>> request = new HttpEntity<>(Arrays.asList(1L, 2L));

        ResponseEntity<ResultResponse> entity = this.restTemplate.exchange("/clients/1/preferences", HttpMethod.PUT,
                request, ResultResponse.class);

        Assert.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertNotNull(entity.getBody());
        Assert.assertNotNull(entity.getBody().getMeta());
        Assert.assertNull(entity.getBody().getData());
        Assert.assertEquals(entity.getBody().getMeta().getStatus(), ResultResponse.Status.ERROR);
    }
}
