package kz.oj.tinkofffintechlab.web.rest.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.oj.tinkofffintechlab.usecase.TranslateTextUseCase;
import kz.oj.tinkofffintechlab.web.rest.v1.dto.request.TranslationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/translation")
@RequiredArgsConstructor
@Tag(name = "Перевод")
public class TranslationController {

    private final TranslateTextUseCase translateTextUseCase;
    @PostMapping
    @Operation(summary = "Перевести текст")
    public ResponseEntity<String> translate(@RequestBody @Valid TranslationRequest request) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(translateTextUseCase.translate(request));
    }
}
