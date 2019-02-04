package com.witcher.rss.db.repository;

import com.witcher.rss.db.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Long> {
}
