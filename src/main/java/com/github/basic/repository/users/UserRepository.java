package com.github.basic.repository.users;


public interface UserRepository {
    UserEntity findUserById(Integer userId);
}
