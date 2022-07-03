package com.blabberchat.core.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewChannelDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String picture;

    @NotNull
    private String description;

    @NotEmpty
    private String ownerUsername;
}
