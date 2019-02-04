package com.witcher.rss.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "theme")
public class ThemeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "themesForNews")
    private Set<NewsModel> news = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "themesForUser")
    private Set<UserModel> users = new HashSet<>();

    public ThemeModel(@NotNull String title) {
        this.title = title;
    }
}
