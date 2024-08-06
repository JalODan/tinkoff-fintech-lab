package kz.oj.tinkofffintechlab.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.oj.tinkofffintechlab.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestLoggingServiceImpl implements RequestLoggingService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void log(String originalText, String targetLanguageCode, String translation) {

        String sql = """
                INSERT INTO request (id, original_text, target_language_code, translated_text, ip_address)
                VALUES ( :id, :original_text, :target_language_code, :translated_text, :ip_address );
        """;

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("id", UUID.randomUUID());
        parameters.addValue("original_text", originalText);
        parameters.addValue("target_language_code", targetLanguageCode);
        parameters.addValue("translated_text", translation);
        parameters.addValue("ip_address", IpUtils.getClientIp(request));

        jdbcTemplate.update(sql, parameters);
    }

}
