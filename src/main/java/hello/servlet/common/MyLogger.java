package hello.servlet.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request")
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // 2. proxy
// target -> class => TARGET_CLASS   /   target -> interface => INTERFACES
// This way, you can create a fake proxy class of MyLogger and inject it into other beans in advance, regardless of the HTTP request
public class MyLogger {
    
    private String uuid;
    private String requestURL;
    
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
    
    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }
    
    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }
    
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}