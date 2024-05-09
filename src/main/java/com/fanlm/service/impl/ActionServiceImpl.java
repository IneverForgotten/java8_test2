package com.fanlm.service.impl;

import com.fanlm.mapper.ActionMapper;
import com.fanlm.entity.Action;
import com.fanlm.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionMapper actionMapper;

    @Override
    public int save() {
        Action action = new Action();
        action.setId(UUID.randomUUID().toString());
        action.setUrl("123456");
        return actionMapper.insert(action);
    }

    @Override
    public int delete() {
        int i = actionMapper.deleteById("1");
        int j = i/0;
        return 1;
    }
}
