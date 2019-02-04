package com.witcher.rss.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "news")
public class NewsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_news", nullable = false)
    @NotNull
    private String linkNews;

    @Column(name = "link_icon", nullable = false)
    @NotNull
    private String linkIcon;

    @Column(name = "date_publication", nullable = false)
    @NotNull
    private LocalDateTime datePublication;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "news_theme",
            joinColumns = { @JoinColumn(name = "news_id") },
            inverseJoinColumns = { @JoinColumn(name = "theme_id") })
    private Set<ThemeModel> themesForNews = new HashSet<>();

    public NewsModel(@NotNull String linkNews, @NotNull String linkIcon, @NotNull LocalDateTime datePublication) {
        this.linkNews = linkNews;
        this.linkIcon = linkIcon;
        this.datePublication = datePublication;
    }

    public void addTheme(ThemeModel themeModel) {
        if (themesForNews != null) themesForNews.add(themeModel);
    }

    public void removeTheme(@NotNull ThemeModel themeModel) {
        if (themesForNews != null) themesForNews.remove(themeModel);
        if (themeModel.getNews() != null) themeModel.getNews().remove(this);
    }
}
