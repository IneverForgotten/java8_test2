package com.fanlm.statemachine;

import com.fanlm.mapper.StateMachineContextMapper;
import com.fanlm.statemachine.action.PassAction;
import com.fanlm.statemachine.event.ActivityEvent;
import com.fanlm.statemachine.guard.RejectConditionGuard;
import com.fanlm.statemachine.guard.SubmitConditionGuard;
import com.fanlm.statemachine.state.ActivityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import javax.annotation.Resource;
import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class ActivityStateMachineAutoConfiguration extends StateMachineConfigurerAdapter<ActivityState, ActivityEvent> {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private ActivityStateService mapper;

    @Resource
    private StateMachineRuntimePersister<ActivityState, ActivityEvent, String> stateMachinePersister;

    /**
     * StateMachineRuntimePersister为状态机运行时持久化配置
     */
    @Bean
    public StateMachineRuntimePersister<String, String, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    @Bean("activityStateMachinePersister")
    public ActivityStateMachinePersister<ActivityState, ActivityEvent, String> stateMachinePersister(ActivityStateService mapper){
        return new ActivityStateMachinePersister(new ActivityStateMachinePersist(mapper));
    }
    @Bean
    public StateMachineService<ActivityState, ActivityEvent> activityEventStateMachineService(StateMachineFactory<ActivityState, ActivityEvent> stateMachineFactory){
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachinePersister);
    }



    @Override
    public void configure(StateMachineStateConfigurer<ActivityState, ActivityEvent> states) throws Exception {
        states.withStates()
                .initial(ActivityState.INIT)
                .states(EnumSet.allOf(ActivityState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ActivityState, ActivityEvent> transitions) throws Exception {
        // 待提交审核 --提交审核--> 待审核
        // @formatter:off
        // 现态-->事件-->次态
        // withExternal是当现态和次态不相同时使用。
        transitions.withExternal()
                .source(ActivityState.INIT).target(ActivityState.DRAFT).event(ActivityEvent.SAVE)
                .and().withExternal()
                .source(ActivityState.DRAFT).target(ActivityState.WAIT_VERIFY).event(ActivityEvent.SUBMIT)
                .guard(applicationContext.getBean(SubmitConditionGuard.class));
        transitions.withExternal().source(ActivityState.WAIT_VERIFY).target(ActivityState.PASSED).event(ActivityEvent.PASS)
                .action(applicationContext.getBean(PassAction.class));
        transitions.withExternal().source(ActivityState.WAIT_VERIFY).target(ActivityState.REJECTED).event(ActivityEvent.REJECT)
                .guard(applicationContext.getBean(RejectConditionGuard.class));
        transitions.withExternal()
                .source(ActivityState.REJECTED)
                .target(ActivityState.WAIT_VERIFY)
                .event(ActivityEvent.SUBMIT)
                .guard(applicationContext.getBean(SubmitConditionGuard.class));

        // 审核通过-->上线-->待上线
        transitions.withExternal().source(ActivityState.PASSED).target(ActivityState.WAIT_ONLIT).event(ActivityEvent.ONLINE);
        // 待上线-->上线-->已上线
        transitions.withExternal().source(ActivityState.WAIT_ONLIT).target(ActivityState.ONLINED).event(ActivityEvent.ONLINE);
        // 已上线-->下线-->已下线
        transitions.withExternal()
                .source(ActivityState.ONLINED).target(ActivityState.OFFLINING).event(ActivityEvent.OFFLINE);
        // 待上线-->下线-->下线中
        transitions.withExternal()
                .source(ActivityState.WAIT_ONLIT).target(ActivityState.OFFLINING).event(ActivityEvent.OFFLINE)
                .and()
                // 已下线-->结束-->已结束
                .withChoice()
                .source(ActivityState.OFFLINING)
                .first(ActivityState.FINISHED, new Guard<ActivityState, ActivityEvent>() {
                    @Override
                    public boolean evaluate(StateContext<ActivityState, ActivityEvent> context) {
                        return true;
                    }
                })
                .last(ActivityState.OFFLINING);
        // @formatter:on
    }
}
