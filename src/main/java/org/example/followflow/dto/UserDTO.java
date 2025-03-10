package org.example.followflow.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;

    @ToString.Exclude
    private String password;
}
