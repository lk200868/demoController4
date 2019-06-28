package com.kgc.service;



import com.kgc.dao.StudMapper;
import com.kgc.entity.Stud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudService extends BaseSQLServiceImpl<Stud, Long, StudMapper> {

    public StudService(){
        super();
    }

}
