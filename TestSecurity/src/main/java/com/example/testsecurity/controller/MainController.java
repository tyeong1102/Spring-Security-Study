package com.example.testsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(Model model) {

        // 현재 로그인한 사용자 아이디
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        // 로그인한 사용자의 role값 검증 로직
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        // 나중에 role에 따라 권한을 달리 해야할 경우 사용 가능함.
        String role = auth.getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }
}
