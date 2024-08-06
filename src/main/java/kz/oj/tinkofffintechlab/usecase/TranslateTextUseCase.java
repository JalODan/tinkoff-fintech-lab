package kz.oj.tinkofffintechlab.usecase;

import kz.oj.tinkofffintechlab.service.RequestLoggingService;
import kz.oj.tinkofffintechlab.service.TranslationService;
import kz.oj.tinkofffintechlab.web.rest.v1.dto.request.TranslationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class TranslateTextUseCase {

    private final TranslationService translationService;
    private final ExecutorService executorService;
    private final RequestLoggingService requestLoggingService;

    public TranslateTextUseCase(TranslationService translationService, RequestLoggingService requestLoggingService) {
        this.translationService = translationService;
        this.requestLoggingService = requestLoggingService;
        executorService = Executors.newFixedThreadPool(10);
    }

    public String translate(TranslationRequest request) throws ExecutionException, InterruptedException {

        String[] words = request.getText().split(" +");

        List<Future<String>> futures = new ArrayList<>();

        for (String word : words) {
            Future<String> future = executorService.submit(() -> translationService.translate(word, request.getTargetLanguage()));
            futures.add(future);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < futures.size(); i++) {
            String s = futures.get(i).get();
            sb.append(s);
            if (i != futures.size() - 1) {
                sb.append(" ");
            }
        }

        String translation = sb.toString();

        requestLoggingService.log(request.getText(), request.getTargetLanguage(), translation);

        return translation;
    }
}
