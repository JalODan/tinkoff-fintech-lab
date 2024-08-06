package kz.oj.tinkofffintechlab.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class IpUtils {

    private static final String[] IP_HEADER_NAMES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "X-Real-IP"
    };

    public static String getClientIp(HttpServletRequest request) {

        if (request == null) {
            return "0.0.0.0";
        }

        return Arrays.stream(IP_HEADER_NAMES)
                .map(request::getHeader)
                .filter(h -> h != null && !h.isEmpty() && !"unknown".equalsIgnoreCase(h))
                .findFirst()
                .orElse(request.getRemoteAddr());
    }
}
