package com.witcher.rss.db.repository;

import com.witcher.rss.db.model.NewsModel;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<NewsModel, Long> {
}
