package com.revature.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
}
