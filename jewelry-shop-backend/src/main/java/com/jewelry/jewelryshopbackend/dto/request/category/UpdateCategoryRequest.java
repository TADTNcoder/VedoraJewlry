package com.jewelry.jewelryshopbackend.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 100, message = "Category name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Category slug must not be blank")
    @Size(max = 120, message = "Category slug must not exceed 120 characters")
    private String slug;

    private String description;

    private Boolean status;
}