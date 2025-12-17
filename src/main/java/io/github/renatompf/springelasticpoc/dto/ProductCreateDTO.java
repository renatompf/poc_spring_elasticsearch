package io.github.renatompf.springelasticpoc.dto;

import io.github.renatompf.springelasticpoc.model.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link ProductEntity}
 */
@Schema(description = "Product creation request")
public record ProductCreateDTO(
        @NotBlank
        @NotNull
        @Schema(description = "Product name", example = "Google Pixel 8a", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
        @NotBlank
        @NotNull
        @Schema(description = "Product description", example = "Google Pixel 8a is a smartphone from Google.", requiredMode = Schema.RequiredMode.REQUIRED)
        String description,
        @NotBlank
        @NotNull
        @Schema(description = "Product category", example = "Smartphones", requiredMode = Schema.RequiredMode.REQUIRED)
        String category,
        @NotBlank
        @NotNull
        @Schema(description = "Product brand", example = "Google", requiredMode = Schema.RequiredMode.REQUIRED)
        String brand,
        @Positive
        @NotNull
        @Schema(description = "Product price", example = "1000.00", requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal price,
        @NotEmpty
        @Schema(description = "Tags where the product can be putt in",
                example = " ['Google', 'Pixel', 'Smartphone', 'Android', 'Mobile']",
                requiredMode = Schema.RequiredMode.REQUIRED)
        List<String> tags
) {}