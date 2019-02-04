package com.witcher.rss.db.repository;

import com.witcher.rss.db.model.ThemeModel;
import org.springframework.data.repository.CrudRepository;

public interface ThemeRepository extends CrudRepository<ThemeModel, Long> {
}
