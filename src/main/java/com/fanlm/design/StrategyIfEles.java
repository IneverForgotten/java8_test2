package com.fanlm.design;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import cn.hutool.core.util.IdUtil;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @ClassName: StrategyIfEles
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2021/8/16 15:51
 */
public class StrategyIfEles {
    private static String MAN = "man";
    private static String WOMAN = "woman";
    private static String WC_EVENT = "想上厕所";

    private static Map<String, Consumer<String>> FUNC_MAP = new ConcurrentHashMap<>();

    static {
        FUNC_MAP.put(MAN, person -> {
            System.out.println(person + "应该去男厕所");
        });
        FUNC_MAP.put(WOMAN, person -> {
            System.out.println(person + "应该去女厕所");
        });
    }

    @Data
    static class Person {
        private String gender;
        private String name;


    }

    public static void main(String[] args) {
        PersonS p = new PersonS();
        p.setGender(MAN);
        p.setName("张三");
        PersonS p2 = new PersonS();
        p2.setGender(WOMAN);
        p2.setName("张三他老婆");
// -----------------------正常人写法：
        if (Objects.equals(p.getGender(), MAN)) {
            System.out.println(p.getName() + "应该去男厕所");
        }
        if (Objects.equals(p.getGender(), WOMAN)) {
            System.out.println(p.getName() + "应该去女厕所");
        }
//------------------------Lambda策略模式写法：
        FUNC_MAP.get(p.getGender()).accept(p.name);
        FUNC_MAP.get(p2.getGender()).accept(p2.name);
//--------------------------.DDD领域驱动设计思想+策略模式写法
        PersonD p3 = PersonFactory.initPerson("张三", MAN);
        PersonD p4 = PersonFactory.initPerson("张三他老婆", WOMAN);
        p3.goToWC();
        p4.goToWC();
//----------------------Actor模型+领域驱动设计+策略模式+事件响应式架构
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef person = actorSystem.actorOf(PersonS.props("张三", MAN), "ZhangSan");
        ActorRef toilet = actorSystem.actorOf(Toilet.props(), "Toilet");
        Pair<String, ActorRef> message = Pair.of(WC_EVENT, toilet);
        person.tell(message, ActorRef.noSender());
    }

    @Data
    static class PersonD {
        private String gender;
        private String name;

        private static Map<String, Consumer<String>> FUNC_MAP = new ConcurrentHashMap<>();

        static {
            FUNC_MAP.put(MAN, person -> {
                System.out.println(person + "应该去男厕所");
            });
            FUNC_MAP.put(WOMAN, person -> {
                System.out.println(person + "应该去女厕所");
            });
        }

        public void goToWC() {
            FUNC_MAP.get(gender).accept(name);
        }
    }

    static class PersonFactory {
        public static PersonD initPerson(String name, String gender) {
            PersonD p = new PersonD();
            p.setName(name);
            p.setGender(gender);
            return p;
        }
    }

    @Data
    static class PersonS extends UntypedActor {
        private String gender;
        private String name;

        public static Props props(final String name, final String gender) {
            return Props.create(new Creator<PersonS>() {
                private static final long serialVersionUID = 1L;

                @Override
                public PersonS create() throws Exception {
                    PersonS p = new PersonS();
                    p.setGender(gender);
                    p.setName(name);
                    return p;
                }
            });
        }

        @Override
        public void onReceive(Object message) throws Throwable {
            Pair<String, ActorRef> m = (Pair<String, ActorRef>) message;
            System.out.println(name + m.getFirst());
//            m.getFirst().tell(this, ActorRef.noSender());
        }
    }

    @Data
    static class Toilet extends UntypedActor {
        private static Map<String, Consumer<String>> FUNC_MAP = new ConcurrentHashMap<>();

        static {
            FUNC_MAP.put(MAN, person -> {
                System.out.println(person + "应该去男厕所");
            });
            FUNC_MAP.put(WOMAN, person -> {
                System.out.println(person + "应该去女厕所");
            });
        }

        public void wc(PersonS p) {
            FUNC_MAP.get(p.getGender()).accept(p.getName());
        }

        public static Props props() {
            return Props.create(Toilet.class);
        }

        @Override
        public void onReceive(Object message) throws Throwable {
            PersonS p = (PersonS) message;
            wc(p);
        }

    }

}
