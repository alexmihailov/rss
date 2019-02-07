package com.witcher.rss.api.rs.admin;

import com.witcher.rss.api.data.NewsData;
import com.witcher.rss.api.data.ResultResponse;
import com.witcher.rss.api.data.ThemeData;
import com.witcher.rss.api.data.UserData;
import com.witcher.rss.domain.exceptions.NewsNotFoundException;
import com.witcher.rss.domain.exceptions.ThemeNotFoundException;
import com.witcher.rss.domain.exceptions.UserNotFoundException;
import com.witcher.rss.domain.services.admin.NewsAdminService;
import com.witcher.rss.domain.util.NewsConverter;
import com.witcher.rss.domain.util.ThemeConverter;
import com.witcher.rss.domain.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.List;

import static com.witcher.rss.domain.util.Preconditions.*;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Service
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminRS {

    private NewsAdminService newsAdminService;

    @POST
    @Path("/theme")
    public ResultResponse<ThemeData> createTheme(ThemeData theme) {

        checkNotNull(theme, "Not set body param!");
        checkNotEmpty(theme.getTitle(), "Param title not set!");

        ThemeData createdTheme = ThemeConverter.convertToThemeDTO(
                newsAdminService.createTheme(theme.getTitle())
        );

        return new ResultResponse<>(createdTheme);
    }

    @PUT
    @Path("/theme")
    public ResultResponse<ThemeData> updateTitleTheme(ThemeData theme) {

        checkNotNull(theme, "Not set body param!");
        checkNotNull(theme.getId(), "Param id not set!");
        checkNotEmpty(theme.getTitle(), "Param title not set!");

        try {
            ThemeData createdTheme = ThemeConverter.convertToThemeDTO(
                    newsAdminService.updateTitleTheme(theme.getId(), theme.getTitle())
            );
            return new ResultResponse<>(createdTheme);
        } catch (ThemeNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @DELETE
    @Path("/theme/{id}")
    public ResultResponse<Void> deleteTheme(@PathParam("id") long themeId) {

        try {
            newsAdminService.deleteTheme(themeId);
            return new ResultResponse<>();
        } catch (ThemeNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @POST
    @Path("/news")
    public ResultResponse<NewsData> createNews(NewsData newsData) {

        checkNotNull(newsData, "Not set body param!");
        checkNotEmpty(newsData.getLinkNews(), "Param linkNews not set!");
        checkNotEmpty(newsData.getLinkIcon(), "Param linkIcon not set!");
        checkNotNull(newsData.getDatePublication(), "Param datePublication not set!");

        NewsData created = NewsConverter.convertToNewsDTO(
                newsAdminService.createNews(newsData.getLinkNews(), newsData.getLinkIcon(),
                        newsData.getDatePublication())
        );

        return new ResultResponse<>(created);
    }

    @PUT
    @Path("/news")
    public ResultResponse<NewsData> updateNews(NewsData newsData) {

        checkNotNull(newsData, "Not set body param!");
        checkNotNull(newsData.getId(), "Param id not set!");
        checkNotEmpty(newsData.getLinkNews(), "Param linkNews not set!");
        checkNotEmpty(newsData.getLinkIcon(), "Param linkIcon not set!");
        checkNotNull(newsData.getDatePublication(), "Param datePublication not set!");

        try {
            NewsData updated = NewsConverter.convertToNewsDTO(
                    newsAdminService.updateNews(newsData.getId(), newsData.getLinkNews(),
                            newsData.getLinkIcon(), newsData.getDatePublication())
            );
            return new ResultResponse<>(updated);
        } catch (NewsNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @DELETE
    @Path("/news/{id}")
    public ResultResponse<Void> deleteNews(@PathParam("id") long newsId) {

        try {
            newsAdminService.deleteNews(newsId);
            return new ResultResponse<>();
        } catch (NewsNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @GET
    @Path("/users")
    public ResultResponse<Collection<UserData>> getAllUsers() {

        Collection<UserData> userDataList = UserConverter.convertToListUserDTO(
                newsAdminService.getAllUsers()
        );

        return new ResultResponse<>(userDataList);
    }

    @GET
    @Path("/user/{id}")
    public ResultResponse<UserData> getUserById(@PathParam("id") long userId) {

        try {
            UserData userData = UserConverter.convertToUserDTO(
                    newsAdminService.getUserById(userId)
            );
            return new ResultResponse<>(userData);
        } catch (UserNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @DELETE
    @Path("/user/{id}")
    public ResultResponse<Void> deleteUser(@PathParam("id") long userId) {

        try {
            newsAdminService.deleteUser(userId);
            return new ResultResponse<>();
        } catch (UserNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @PUT
    @Path("news/{id}/references")
    public ResultResponse<Collection<Long>> updateThemesReferences(@PathParam("id") long newsId,
                                                                   List<Long> themeIds) {

        checkNotNull(themeIds, "Not set body param!");

        try {
            Collection<Long> savedIds = newsAdminService.updateThemesReferences(newsId, themeIds);
            return new ResultResponse<>(savedIds);
        } catch (ThemeNotFoundException | NewsNotFoundException e) {
            throw webApplicationException(NOT_FOUND, e.getMessage());
        }
    }

    @Autowired
    public void setNewsAdminService(NewsAdminService newsAdminService) {
        this.newsAdminService = newsAdminService;
    }
}
