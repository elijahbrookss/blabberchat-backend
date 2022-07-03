package com.blabberchat.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    @NotEmpty(message = "Role must have name")
    private String name;
}
