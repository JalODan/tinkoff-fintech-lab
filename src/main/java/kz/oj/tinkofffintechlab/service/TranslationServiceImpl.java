package kz.oj.tinkofffintechlab.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.translate.v3.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final TranslationServiceClient translationServiceClient;
    private final Environment environment;
    private String projectId;

    @PostConstruct
    public void init() throws IOException {

        String path = environment.getProperty("GOOGLE_APPLICATION_CREDENTIALS");
        if (path == null) {
            throw new RuntimeException("Переменная \"GOOGLE_APPLICATION_CREDENTIALS\" не установлена");
        }

        File file = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        Map<String, Object> map = objectMapper.readValue(file, typeRef);

        this.projectId = (String) map.get("project_id");
    }

    @Override
    public String translate(String text, String targetLanguageCode) {

        TranslateTextRequest request =
                TranslateTextRequest.newBuilder()
                        .setParent(LocationName.of(projectId, "global").toString())
                        .setMimeType("text/plain")
                        .setTargetLanguageCode(targetLanguageCode)
                        .addContents(text)
                        .build();

        TranslateTextResponse translateTextResponse = translationServiceClient.translateText(request);
        return translateTextResponse.getTranslationsList().stream()
                .map(Translation::getTranslatedText)
                .findAny().orElse(text);
    }
}
