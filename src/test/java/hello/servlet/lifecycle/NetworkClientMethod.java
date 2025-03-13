package hello.servlet.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClientMethod {
    
    private String url;
    
    public NetworkClientMethod() {
        System.out.println();
        System.out.println("===== initMethod/destroyMethod =====");
        System.out.println("생성자 호출, url = " + url);
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }
    
    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }
    
    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    public void init() {
        //의존관계 주입이 끝나면 호출
        System.out.println("NetworkClientMethod.init");

        connect();  //객체 생성
        call("초기화 연결 메시지");
    }
    
    public void close() {
        System.out.println("NetworkClientMethod.close");
        disconnect();
    }

}