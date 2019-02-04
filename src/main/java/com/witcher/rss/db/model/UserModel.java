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
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    @NotNull
    private String fullName;

    @Column(name = "login", nullable = false, unique = true)
    @NotNull
    private String login;

    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_theme",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "theme_id") })
    private Set<ThemeModel> themesForUser = new HashSet<>();

    public UserModel(@NotNull String fullName, @NotNull String login, @NotNull String password) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }

    public void addTheme(ThemeModel themeModel) {
        if (themesForUser != null) themesForUser.add(themeModel);
    }

    public void removeTheme(@NotNull ThemeModel themeModel) {
        if (themesForUser != null) themesForUser.remove(themeModel);
        if (themeModel.getUsers() != null) themeModel.getUsers().remove(this);
    }
}
