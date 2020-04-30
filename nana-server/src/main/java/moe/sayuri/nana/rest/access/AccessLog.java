package moe.sayuri.nana.rest.access;

import lombok.extern.slf4j.Slf4j;
import moe.sayuri.nana.core.entity.UserLog;
import moe.sayuri.nana.core.type.MdcName;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class AccessLog extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestId = UUID.randomUUID().toString();
        String startTime = Long.toString(new Date().getTime());
        MDC.put(MdcName.requestId.name(), requestId);
        MDC.put(MdcName.startTime.name(), startTime);
        MDC.put(MdcName.result.name(), HttpStatus.OK.toString());
        MDC.put(MdcName.user.name(), request.getHeader("X-USER-ACCOUNT"));
        log.info("requestId:{}, uri:{}, params:{}, method:{}, ip:{}, user:{}",
                requestId, request.getRequestURI(), toParameterString(request.getParameterMap()),
                request.getMethod(), request.getLocalAddr(), request.getHeader("X-USER-ACCOUNT"));
        return true;
    }


    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        Instant endTime = new Date().toInstant();
        String requestId = MDC.get(MdcName.requestId.name());
        Instant startTime = Instant.ofEpochMilli(Long.parseLong((String) MDC.get(MdcName.startTime.name())));
        log.info("request finished: requestId: {}, status: {}, duration: {}",
                requestId, response.getStatus(), Duration.between(startTime, endTime));
    }


    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        try {
            logRequest(request, response, ex);
        } finally {
            tearDown();
        }
    }

    private void tearDown() {
        for (MdcName mdcName: MdcName.values()) {
            MDC.remove(mdcName.name());
        }
    }

    private void logRequest(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        UserLog userLog = new UserLog();
        userLog.setRequestId(MDC.get(MdcName.requestId.name()));
        userLog.setUri(request.getRequestURI());
        userLog.setParams(toParameterString(request.getParameterMap()));
        userLog.setBody(MDC.get(MdcName.body.name()));
        userLog.setMethod(request.getMethod());
        userLog.setIp(request.getLocalAddr());
        userLog.setUser(request.getHeader("X-USER-ACCOUNT"));
        userLog.setStartTime(new Date(Long.parseLong(MDC.get(MdcName.startTime.name()))));
        userLog.setEndTime(new Date());
        userLog.setResult(MDC.get(MdcName.result.name()));
        userLog.setException(MDC.get(MdcName.exception.name()));
        log.info("{}", userLog);
    }

    private String toParameterString(Map<String, String[]> params) {
        return params.entrySet().stream()
                .map(e -> Arrays.stream(e.getValue()).map(v -> e.getKey() + "=" + v)
                        .reduce((v1, v2) ->v1 + "&" + v2).orElse("")
                )
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }

}
