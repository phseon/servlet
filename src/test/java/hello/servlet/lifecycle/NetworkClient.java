package hello.servlet.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {
    
    private String url;
    
    public NetworkClient() {
        System.out.println("===== implements InitializingBean, DisposableBean =====");
        System.out.println("생성자 호출, url = " + url);
        //implements InitializingBean 추가 후 afterPropertiesSet()로 이동
//        connect();  //객체 생성
//        call("초기화 연결 메시지");
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

    //implements InitializingBean, DisposableBean 추가 후 구현
    @Override
    public void afterPropertiesSet() throws Exception {
        //의존관계 주입이 끝나면 호출
        System.out.println("NetworkClient.afterPropertiesSet");

        connect();  //객체 생성
        call("초기화 연결 메시지");
    }
    
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }

}