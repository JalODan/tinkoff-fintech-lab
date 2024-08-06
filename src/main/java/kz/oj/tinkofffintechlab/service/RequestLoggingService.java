package kz.oj.tinkofffintechlab.service;

public interface RequestLoggingService {
    void log(String originalText, String targetLanguageCode, String translation);
}
