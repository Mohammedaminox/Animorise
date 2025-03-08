package com.animo.animorise.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ActivityTypeDto {
    private Long id;
    private String name;
    private MultipartFile icon;
    private String iconPath; // New field for icon file path
}