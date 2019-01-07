package com.cat.controller;

import com.cat.pojo.Options;
import com.cat.pojo.Subject;
import com.cat.pojo.User;
import com.cat.service.SubjectService;
import com.cat.util.Label;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制类
 */
@Controller
public class SubjectController {
    private Map<String,String> map = new HashMap<>();
    @Resource(name = "subjectService")
    private SubjectService subjectService;

    private void initMethod(){
        map.put(Label.PAGENUM,"1");
        map.put(Label.PAGESIZE,"3");
        map.put(Label.TITLE,null);
    }
    @GetMapping("/initVotelist")
    public String initVotelist(Model model){
        initMethod();
        Page<Subject> page = subjectService.selectVotelist(map);
        model.addAttribute(Label.PAGE,page);
        return "votelist";
    }
    @GetMapping("/page")
    public String page(String pageNum,Model model){
        map.put(Label.PAGENUM,pageNum);
        Page<Subject> page = subjectService.selectVotelist(map);
        model.addAttribute(Label.PAGE,page);
        return "votelist";
    }
    @PostMapping("/titleFuzzy")
    public String title(String title,Model model){
        System.out.println("title=====>title："+title);
        map.put(Label.TITLE,title);
        map.put(Label.PAGESIZE,"3");
        map.put(Label.PAGENUM,"1");
        Page<Subject> page = subjectService.selectVotelist(map);
        model.addAttribute(Label.PAGE,page);
        return "votelist";
    }
    @GetMapping("/voteView")
    public String voteView(int sid,Model model){
        Subject subject = subjectService.seleVoteView(sid);
        model.addAttribute(Label.SUBJECT,subject);
        return "voteview";
    }
    @GetMapping("/vote")
    public String vote(int sid, Model model){
        System.out.println("vote====>sid："+sid);
        Subject subject = subjectService.selectVote(sid);
        model.addAttribute(Label.SUBJECT,subject);
        return "vote";
    }
    @PostMapping("/ajaxTitle")
    public void ajaxTitle(String title, HttpServletResponse response) throws IOException {
        System.out.println("ajaxTitle====>"+title);
        boolean result = subjectService.verifyTitle(title);
        PrintWriter out = response.getWriter();
        System.out.println("ajaxTitle====>result:"+result);
        out.print(result);
        out.flush();
        out.close();
    }
    @PostMapping("/addSubjectAndOptions")
    public String addSubjectAndOptions(String title,String type,
                                       @RequestParam  List<String> options,
                                       HttpSession session){
        System.out.println("addSubjectAndOptions====>title:"+title);
        System.out.println("addSubjectAndOptions====>type:"+type);
        Subject subject = new Subject(title,type);
        List<Options> optionsA = new ArrayList<>();
        for(String s : options) {
            System.out.println("addSubjectAndOptions====>options:"+s);
            Options o = new Options(s);
            optionsA.add(o);
        }
        try {
            boolean result = subjectService.addSubject(subject,optionsA);
            if(result){
                session.setAttribute(Label.MESSAGE,"操作成功！");
                return "message";
            }
        }catch (SQLException e){
            session.setAttribute(Label.ERROR,"操作异常！");
            e.printStackTrace();
            return "error";
        }
        return "add";
    }
    @GetMapping("/initMaintain")
    public String initMaintain(HttpSession session,Model model){
        boolean result = yanZhen(session);
        if(!result) {
            session.setAttribute(Label.ERROR,"用户权限不足，请联系管理员！");
            return "error";
        }
        initMethod();
        Page<Subject> page = subjectService.selectVotelist(map);
        model.addAttribute(Label.PAGE,page);
        return "maintain";
    }
    @GetMapping("/pageMaintain")
    public String pageMaintain(String pageNum,Model model){
        map.put(Label.PAGENUM,pageNum);
        Page<Subject> page = subjectService.selectVotelist(map);
        model.addAttribute(Label.PAGE,page);
        return "maintain";
    }
    @GetMapping("/updateSubject")
    public String updateSubject(int sid,Model model){
        System.out.println("updateSubject====>sid："+sid);
        Subject subject = subjectService.selectVote(sid);
        model.addAttribute(Label.SUBJECT,subject);
        return "update";
    }
    @PostMapping("/updateOptions")
    public String updateOptions(int sid,String type,
                                @RequestParam List<String> options,
                                @RequestParam List<Integer> oid,
                                HttpSession session){
        System.out.println("updateOptions====>sid："+sid);
        System.out.println("updateOptions====>type:"+type);
        System.out.println("updateOptions====>options:"+options.size());
        System.out.println("updateOptions====>oid:"+oid.size());
        String label ="";
        List<Options> newOptions = new ArrayList<>();
        Subject subject = new Subject(sid,type);
        List<Options> optionsList = new ArrayList<>();

        int index = options.size()-oid.size();
        if(index>0){
            //添加
            for(int i=options.size()-index;i<options.size();i++){
                System.out.println("添加选项部分修改newOptions====>content:"+options.get(i));
                Options op = new Options(options.get(i),sid);
                newOptions.add(op);
            }
            //修改部分
            for(int k=0;k<oid.size();k++){
                System.out.println("修改选项时新增之添加部分====>content:"+options.get(k)+"\toid:"+oid.get(k));
                Options o = new Options(oid.get(k),options.get(k));
                optionsList.add(o);
            }
            label = "add";
        }else if(index<0){
            //删除
            for(int i=oid.size()-(-index);i<oid.size();i++){
                System.out.println(i);
                System.out.println("删除选项部分修改newOptions====>oid:"+oid.get(i));
                Options op = new Options(oid.get(i));
                newOptions.add(op);
            }
            //修改部分
            for(int j=0;j<options.size();j++){
                System.out.println("修改选项时删除之修改部分====>content:"+options.get(j)+"\toid:"+oid.get(j));
                Options o = new Options(oid.get(j),options.get(j));
                optionsList.add(o);
            }
            label = "del";
        }else{
            //没删除也没新增选项,只修改
            for(int i =0;i<options.size();i++){
                System.out.println("没删除也没新增选项,只修改====>content:"+options.get(i)+"\toid:"+oid.get(i));
                Options o = new Options(oid.get(i),options.get(i));
                optionsList.add(o);
            }
        }
        subject.setOptionsList(optionsList);
        try{
            boolean result = subjectService.updaSubject(subject,newOptions,label);
            if(result){
                session.setAttribute(Label.MESSAGE,"操作成功！");
                return "message";
            }
        }catch(SQLException e){
            e.printStackTrace();
            session.setAttribute(Label.ERROR,"操作异常！");
            return "error";
        }
        session.setAttribute(Label.MESSAGE,"操作失败！");
        return "update";
    }
    @GetMapping("/deleSubAndOpt")
    public String deleSubAndOpt(int sid,HttpSession session){
        try{
            subjectService.deleSubject(sid);
        }catch (SQLException e){
            session.setAttribute(Label.ERROR,"操作异常！");
            e.printStackTrace();
        }
        return "redirect:initMaintain";
    }
    @GetMapping("/addCat")
    public String addCat(HttpSession session){
        boolean result = yanZhen(session);
        if(!result){
            session.setAttribute(Label.ERROR,"用户权限不足，请联系管理员！");
            return "error";
        }
        return "add";
    }
    private boolean yanZhen(HttpSession session){
        User user =(User) session.getAttribute(Label.USER);
        if(user==null) {
            return false;
        }
        return foo(user);
    }
    private boolean foo(User user){
        if(Label.COMMON.equals(user.getIsAdmin())){
            return false;
        }else{
            return true;
        }
    }
}
