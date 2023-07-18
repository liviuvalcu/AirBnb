package com.reply.airbnbdemo.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private String email;
    private String about;
    private String lName;
    private String fname;
    private HostDto host;
}
