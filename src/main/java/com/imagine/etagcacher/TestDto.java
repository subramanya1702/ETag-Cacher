package com.imagine.etagcacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto implements Serializable {

    private static final long serialVersionUID = 4651837280003421204L;

    private String id;
    private String name;
    private String gender;
    private String company;
    private String about;
    private List<FriendDto> friends;
    private String greeting;
}
