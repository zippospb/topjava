package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private ReloadableResourceBundleMessageSource messageSource;

    private final static Map<String, String> I18N_ERROR_MSG = new HashMap<>();

    static {
        I18N_ERROR_MSG.put("meals_unique_user_datetime_idx", "meal.dateConflict");
        I18N_ERROR_MSG.put("users_unique_email_idx", "user.emailConflict");
    }

    @Autowired
    public ExceptionInfoHandler(ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        ErrorType errorType = DATA_ERROR;
        Throwable rootCause = ValidationUtil.getRootCause(e);
        String i18Key = I18N_ERROR_MSG.keySet().stream()
                .filter(rootCause.getMessage() :: contains)
                .findFirst()
                .orElse("");

        String msg = i18Key.isEmpty() ? rootCause.getLocalizedMessage() :
                messageSource.getMessage(I18N_ERROR_MSG.get(i18Key), null, req.getLocale());
        log(req, rootCause, false, errorType);
        return new ErrorInfo(req.getRequestURL(), errorType, msg);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorInfo methodArgumentNotValidError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BindException.class)
    public ErrorInfo bindError(HttpServletRequest req, BindException e) {
        ErrorType errorType = ErrorType.VALIDATION_ERROR;
        log(req, e, false, errorType);
        return new ErrorInfo(req.getRequestURL(), errorType, ValidationUtil.getErrorResponse(e.getBindingResult()));
    }

//    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log(req, rootCause, logException, errorType);
        return new ErrorInfo(req.getRequestURL(), errorType, ValidationUtil.getMessage(rootCause));
    }

    private static void log(HttpServletRequest req, Throwable rootCause, boolean logException, ErrorType errorType){
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
    }
}