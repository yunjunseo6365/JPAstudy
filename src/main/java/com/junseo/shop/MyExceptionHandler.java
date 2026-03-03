package com.junseo.shop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MyExceptionHandler {

    // ItemService의 saveItem 메소드 실행시 발생하는 오류처리들
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerTitle(IllegalArgumentException e){
        // e.getMessage()를 통해 서비스에서 작성한 에러 문구를 그대로 전달합니다.
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handler1(){
        return ResponseEntity.status(400).body("");
    }

    // 모든 API의 에러를 캐치하는 방법
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(){
        return ResponseEntity.status(400).body("에러");
    }
}
