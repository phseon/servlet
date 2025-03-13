package hello.servlet.web;

import hello.servlet.common.MyLogger;
import hello.servlet.logdemo.LogDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
@Controller
@RequiredArgsConstructor
public class LogDemoController {
    
    private final LogDemoService logDemoService;
    
//    private final MyLogger myLogger;
    private final ObjectProvider<MyLogger> myLoggerProvider;
    
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();
        MyLogger myLogger = myLoggerProvider.getObject(); // 1. Using Provider
        myLogger.setRequestURL(requestURL);
        
        myLogger.log("controller test");
        Thread.sleep(1000); // Even if another request comes in before calling the service logic in the first request, each bean is assigned and managed separately
        logDemoService.logic("testId");
        System.out.println("myLogger = " + myLogger.getClass());
        
        return "OK";
    }
}