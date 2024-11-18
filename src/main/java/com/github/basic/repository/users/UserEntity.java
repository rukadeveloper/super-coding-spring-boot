package com.github.basic.repository.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Integer userId;
    private String userName;
    private String likeTravelPlace;
    private String phoneNum;
}
