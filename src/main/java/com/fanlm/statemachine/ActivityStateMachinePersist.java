package com.fanlm.statemachine;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fanlm.statemachine.event.ActivityEvent;
import com.fanlm.statemachine.state.ActivityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.kryo.MessageHeadersSerializer;
import org.springframework.statemachine.kryo.StateMachineContextSerializer;
import org.springframework.statemachine.kryo.UUIDSerializer;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * 持久化配置
 */
public class ActivityStateMachinePersist implements StateMachinePersist<ActivityState, ActivityEvent, String> {

    private final ActivityStateService activityStateService;

    public ActivityStateMachinePersist(ActivityStateService activityStateService) {
        this.activityStateService = activityStateService;
    }


    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.addDefaultSerializer(StateMachineContext.class, new StateMachineContextSerializer());
        kryo.addDefaultSerializer(MessageHeaders.class, new MessageHeadersSerializer());
        kryo.addDefaultSerializer(UUID.class, new UUIDSerializer());
        return kryo;
    });


    @Override
    public void write(StateMachineContext<ActivityState, ActivityEvent> context, String id) throws Exception {
        activityStateService.save(Activity.builder().machineId(id).state(context.getState()).build());
    }

    @Override
    public StateMachineContext<ActivityState, ActivityEvent> read(String id) throws Exception {
        Activity activity = activityStateService.findById(id);
        StateMachineContext<ActivityState, ActivityEvent> context = new DefaultStateMachineContext<>(activity.getState(), null, null, null);
        return null;
    }

    private byte[] serialize(StateMachineContext<ActivityState, ActivityEvent> context) {
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Output output = new Output(out);
        kryo.writeObject(output, context);
        output.close();
        return out.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private StateMachineContext<ActivityState, ActivityEvent> deserialize(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Input input = new Input(in);
        return kryo.readObject(input, StateMachineContext.class);
    }
}
