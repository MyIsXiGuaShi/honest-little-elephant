package com.cat.service.impl;

import com.cat.dao.ItemMapper;
import com.cat.dao.OptionsMapper;
import com.cat.dao.SubjectMapper;
import com.cat.pojo.Options;
import com.cat.pojo.Subject;
import com.cat.service.SubjectService;
import com.cat.util.Label;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Transactional
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {
    @Resource(name = "subjectMapper")
    private SubjectMapper subjectMap;
    @Resource(name = "optionsMapper")
    private OptionsMapper optionsMap;
    @Resource(name = "itemMapper")
    private ItemMapper itemMap;

    @Override
    @Transactional(readOnly = true)
    public Page<Subject> selectVotelist(Map<String, String> map) {
        Integer pageNum = Integer.valueOf(map.get(Label.PAGENUM));
        Integer pageSize = Integer.valueOf(map.get(Label.PAGESIZE));
        System.out.println("selectVotelist====>pageNum:"+pageNum);
        System.out.println("selectVotelist====>pageSize:"+pageSize);
        Page<Subject> page = PageHelper.startPage(pageNum,pageSize);
        subjectMap.selectVotelist(map.get(Label.TITLE));
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Subject seleVoteView(int sid) {
        System.out.println("seleVoteView====>sid"+sid);
        return subjectMap.seleVoteView(sid);
    }

    @Override
    @Transactional(readOnly = true)
    public Subject selectVote(int sid) {
        System.out.println("selectVote====>sid"+sid);
        return subjectMap.selectVote(sid);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyTitle(String title) {
        Integer result = subjectMap.verifyTitle(title);
        if(result!=null && result>0){
            System.out.println("verifyTitle====>result："+true);
            return true;
        }
        System.out.println("verifyTitle====>result："+false);
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = SQLException.class)
    public boolean addSubject(Subject subject, List<Options> options) throws SQLException {
        int resultA = subjectMap.addSubject(subject);
        boolean falg = false;
        System.out.println("addSubject====>sid:"+subject.getSid());
        if(resultA>0){
            if(subject.getSid()==0){
                throw new SQLException();
            }
            for(Options o : options){
                o.setOsid(subject.getSid());
            }
            int resultB = optionsMap.addOptions(options);
            if(resultB>0){
                falg = true;
            }
        }
        return falg;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = SQLException.class)
    public boolean updaSubject(Subject subject, List<Options> newOptions,String label) throws SQLException {
        int resultA = subjectMap.updaSubject(subject);
        int resultB = optionsMap.updaOptions(subject.getOptionsList());
        if(newOptions.size()>0 && "add".equals(label)){
            int resultC = optionsMap.addOptions(newOptions);
            if(resultA>0 && resultB>0 && resultC>0){
                return true;
            }else{
                throw new SQLException();
            }
        }else if(newOptions.size()>0 && "del".equals(label)){
            int resultC = optionsMap.deleOptions(newOptions);
            if(resultA>0 && resultB>0 && resultC>0){
                return true;
            }else{
                throw new SQLException();
            }
        }else{
            if(resultA>0 && resultB>0){
                return true;
            }else{
                throw new SQLException();
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = SQLException.class)
    public boolean deleSubject(int sid)throws SQLException {
        int resultA = itemMap.deleItem(sid);
        int resultB = optionsMap.deleOptionsOsid(sid);
        int resultC = subjectMap.deleSubject(sid);
        if((resultA>0 && resultB>0) || resultC>0){
            return true;
        }else{
            throw new SQLException();
        }
    }
}
