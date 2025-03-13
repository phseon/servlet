package hello.servlet.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        //같은 클래스 내부에서 선언된 'static' 클래스(PrototypeBean)는 외부 클래스명(PrototypeTest)을 생략할 수 있음
        //넘겨준 클래스를 설정 클래스(@Configuration)처럼 취급하여 자동으로 빈 등록. 따라서 '@Component'가 없어도 'PrototypeBean'이 자동으로 스프링 빈으로 등록됨
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class); //첫번째 조회
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class); //두번째 조회
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
        
        //프로토타입 빈은 컨테이너가 자동으로 소멸시키지 않으므로, 필요 시 직접 'destroy()'를 호출해야 @PreDestroy 메서드가 실행됨
        prototypeBean1.destroy();
        prototypeBean2.destroy();
        
        //컨테이너가 닫혀도 프로토타입 빈은 스프링 컨테이너에서 관리하지 않기 때문에 @PreDestroy 메서드가 실행되지 않음
        ac.close();
    }

    @Scope("prototype")
    //PrototypeTest 내부에 선언된 정적 클래스이므로 PrototypeBean.class로 직접 접근 가능
    //PrototypeBean이 static이 아니면 오류 발생 → PrototypeTest의 인스턴스를 먼저 생성해야 함
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
