package com.witcher.rss.rs;

import com.witcher.rss.api.data.ResultResponse;
import com.witcher.rss.api.data.ThemeData;
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

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminRSTes {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateTheme() {

        HttpEntity<ThemeData> request = new HttpEntity<>(ThemeData.builder().title("test").build());

        ResponseEntity<ResultResponse> entity = this.restTemplate.exchange("/admin/theme", HttpMethod.POST,
                request, ResultResponse.class);

        Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(entity.getBody());
        Assert.assertNotNull(entity.getBody().getMeta());
        Assert.assertNotNull(entity.getBody().getData());
        Assert.assertEquals(entity.getBody().getMeta().getStatus(), ResultResponse.Status.OK);
    }

    @Test
    public void testUpdateTitleThemeWhenNotFound() {

        HttpEntity<ThemeData> request = new HttpEntity<>(ThemeData.builder()
                .title("test")
                .id(2L)
                .build()
        );

        ResponseEntity<ResultResponse> entity = this.restTemplate.exchange("/admin/theme", HttpMethod.PUT,
                request, ResultResponse.class);

        Assert.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertNotNull(entity.getBody());
        Assert.assertNotNull(entity.getBody().getMeta());
        Assert.assertNull(entity.getBody().getData());
        Assert.assertEquals(entity.getBody().getMeta().getStatus(), ResultResponse.Status.ERROR);
    }
}