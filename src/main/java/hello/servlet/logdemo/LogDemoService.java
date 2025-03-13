package hello.servlet.logdemo;

import hello.servlet.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class LogDemoService {
//    private final MyLogger myLogger;
    private final ObjectProvider<MyLogger> myLoggerProvider;
    
    public void logic(String id) {
        MyLogger myLogger = myLoggerProvider.getObject(); //1. Using provider
        myLogger.log("service id = " + id);
    }
}