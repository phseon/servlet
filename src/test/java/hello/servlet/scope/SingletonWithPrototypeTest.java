package hello.servlet.scope;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest {
    
    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);
        
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }
    
    @Scope("singleton")
    static class ClientBean {
        
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider; //지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스 제공
//        
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); //항상 새로운 프로토타입 빈이 생성됨
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
        
        @Autowired
        private Provider<PrototypeBean> provider; //지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스 제공
        
        public int logic() {
            PrototypeBean prototypeBean = provider.get(); //항상 새로운 프로토타입 빈이 생성됨
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
    
    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;
        public void addCount() {
            count++;
        }
        
        public int getCount() {
            return count;
        }
        
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }
        
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}