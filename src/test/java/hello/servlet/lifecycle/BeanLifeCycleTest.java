package hello.servlet.lifecycle;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); //스프링 컨테이너를 종료함
        //ConfigurableApplicationContext 필요 -> 보통 닫는 메서드는 잘 안 쓰기 때문에 기본 애플리케이션 컨텍스트가 직접 제공해 주지 않음
    }
    
    @Configuration
    static class LifeCycleConfig {
        
        @Bean   //빈 생성과 의존성 주입을 설정한 후, 설정이 끝난 객체를 반환하는 방식
        //Spring 빈 컨테이너에서 객체를 싱글톤으로 관리하고 싶을 때 사용. 다른 컴포넌트에서 이 객체를 사용할 수 있게 됨
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();  //객체 생성
            networkClient.setUrl("http://hello-spring.dev");    //의존성 주입. 일반적으로 객체 생성 후 'set' 메서드를 통해 이루어짐

            //의존성 주입이 끝난 후 객체 반환. 
            return networkClient;
        }
        
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClientMethod networkClientMethod() {
            NetworkClientMethod networkClientMethod = new NetworkClientMethod();
            networkClientMethod.setUrl("http://hello-spring.dev");
 
            return networkClientMethod;
        }
        
        @Bean
        public NetworkClientAnnotation networkClientAnnotation() {
            NetworkClientAnnotation networkClientAnnotation = new NetworkClientAnnotation();
            networkClientAnnotation.setUrl("http://hello-spring.dev");
            
            return networkClientAnnotation;
        }
    }
}