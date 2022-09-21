package com.imagine.etagcacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto implements Serializable {

    private static final long serialVersionUID = 3987885526152041598L;

    private int id;
    private String name;
}
