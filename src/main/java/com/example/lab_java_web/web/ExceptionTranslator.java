package com.example.lab_java_web.web;

import com.example.lab_java_web.featuretoggle.exception.FeatureToggleNotEnabledException;
import com.example.lab_java_web.service.exeption.CosmoCatNofFoundException;
import com.example.lab_java_web.service.exeption.OrderNotFoundException;
import com.example.lab_java_web.service.exeption.ParamsViolationException;
import com.example.lab_java_web.service.exeption.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static com.example.lab_java_web.util.ValidationUtils.getValidationProblemDetail;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(ProductNotFoundException ex) {
        log.info("Product Not Found exception raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("product-not-found"));
        problemDetail.setTitle("Product Not Found");
        return ResponseEntity.status(NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(CosmoCatNofFoundException.class)
    public ResponseEntity<ProblemDetail> handleCosmoCatNofFoundException(CosmoCatNofFoundException ex) {
        log.info("Cosmo Cat Not Found exception raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("cosmo-cat-not-found"));
        problemDetail.setTitle("Cosmo Cat Not Found");
        return ResponseEntity.status(NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleOrderNotFoundException(OrderNotFoundException ex) {
        log.info("Order Not Found exception raised");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("order-not-found"));
        problemDetail.setTitle("Order Not Found");
        return ResponseEntity.status(NOT_FOUND).body(problemDetail);
    }
    @ExceptionHandler(FeatureToggleNotEnabledException.class)
    ProblemDetail handleFeatureToggleNotEnabledException(FeatureToggleNotEnabledException ex) {
        log.info("Feature is not enabled");
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("feature-disabled"));
        problemDetail.setTitle("Feature is disabled");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ParamsViolationException> validationResponse =
                errors.stream().map(err -> ParamsViolationException.builder().reason(err.getDefaultMessage()).fieldName(err.getField()).build()).toList();
        log.info("Input params validation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getValidationProblemDetail(validationResponse));
    }
}
