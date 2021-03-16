package com.springboot.attendsys.service;

import com.springboot.attendsys.mapper.SelectedMapper;
import com.springboot.attendsys.model.Selected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedService {
    @Autowired
    SelectedMapper selectedMapper;

    public Selected getByIds(int cid, int uid) {
        return selectedMapper.getByIds(cid,uid);
    }
}
