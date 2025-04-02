package com.animo.animorise.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDto {
    private Long id;
    private String name;
    private MultipartFile icon;
    private String iconPath;

}