package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class DMPController {
    KakaoAPI kakaoApi = new KakaoAPI();

    @RequestMapping("/test")
    public void test() {
    	System.out.println("test");
    }
    
    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // 1.번인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
        
        System.out.println("login info :" + userInfo.toString());

        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("accessToken", accessToken);
        }
        mav.addObject("userId", userInfo.get("email"));
        mav.setViewName("index");
        System.out.println(mav.getModel());
        
        return mav;
    }

    @RequestMapping(value= "/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        kakaoApi.kakaoLogout((String) session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        mav.setViewName("index");
       
        return mav;
    }
    @RequestMapping(value="/kakaounlink")
    public String unlink(HttpSession session) {
    	kakaoApi.unlink((String)session.getAttribute("access_token")); 
   session.invalidate(); 
   return "redirect:/";
    }

    
}
