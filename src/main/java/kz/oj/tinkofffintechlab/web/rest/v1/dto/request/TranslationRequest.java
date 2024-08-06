package kz.oj.tinkofffintechlab.web.rest.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TranslationRequest {

    @NotBlank
    private String sourceLanguage;

    @NotBlank
    private String targetLanguage;

    @NotBlank
    @Size(max = 1000)
    private String text;
}
