package com.witcher.rss.api.rs.client;

import com.witcher.rss.api.data.NewsData;
import com.witcher.rss.api.data.ResultResponse;
import com.witcher.rss.api.data.ThemeData;
import com.witcher.rss.domain.exceptions.NewsNotFoundException;
import com.witcher.rss.domain.exceptions.ThemeNotFoundException;
import com.witcher.rss.domain.exceptions.UserNotFoundException;
import com.witcher.rss.domain.model.News;
import com.witcher.rss.domain.services.client.NewsClientService;
import com.witcher.rss.domain.util.NewsConverter;
import com.witcher.rss.domain.util.ThemeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

import static com.witcher.rss.domain.util.WebExceptionsHelper.webApplicationException;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Service
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
public class ClientRS {

    private NewsClientService clientService;

    @GET
    @Path("/themes")
    public ResultResponse<Collection<ThemeData>> getAllThemes() {

        Collection<ThemeData> response = ThemeConverter.convertToListThemeDTO(
                clientService.getAllThemes()
        );

        return new ResultResponse<>(response);
    }

    @GET
    @Path("/theme/{id}")
    public ResultResponse<ThemeData> getTheme(@PathParam("id") long themeId) {

        try {
            ThemeData themeData = ThemeConverter.convertToThemeDTO(
                    clientService.getThemeById(themeId)
            );
            return new ResultResponse<>(themeData);
        } catch (ThemeNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @GET
    @Path("/news/{id}")
    public ResultResponse<NewsData> getNewsById(@PathParam("id") long newsId) {

        try {
            NewsData newsData = NewsConverter.convertToNewsDTO(
                    clientService.getNewsById(newsId)
            );
            return new ResultResponse<>(newsData);
        } catch (NewsNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @GET
    @Path("/news")
    public ResultResponse<Collection<NewsData>> getNewsByThemeIds(@QueryParam("ids") Collection<Long> themeIds) {

        try {
            Collection<NewsData> newsData = NewsConverter.convertToListNewsDTO(
                    clientService.getNewsByThemeIds(themeIds)
            );
            return new ResultResponse<>(newsData);
        } catch (ThemeNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @PUT
    @Path("/{id}/preferences")
    public ResultResponse<Collection<Long>> updateUserPreferences(@PathParam("id") long userId,
                                                                  Collection<Long> themeIds) {
        try {
            Collection<Long> savedIds = clientService.updateUserPreferences(userId, themeIds);
            return new ResultResponse<>(savedIds);
        } catch (UserNotFoundException | ThemeNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @Autowired
    public void setClientService(NewsClientService clientService) {
        this.clientService = clientService;
    }
}
