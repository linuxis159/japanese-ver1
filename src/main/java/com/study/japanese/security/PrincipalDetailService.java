package com.study.japanese.security;

import com.study.japanese.entity.User;
import com.study.japanese.repository.UserRepository;
import com.study.japanese.security.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    //스프링이 로그인 요청을 가로챌 때 username과 password를 가로채는데
    //password 부분처리는 알아서함.
    //username이 DB에 있는지만 확인해주면 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findById(username).orElseThrow(() -> {
            return new UsernameNotFoundException("해당사용자를 찾을 수 없습니다");
        });
        return new PrincipalDetail(principal); //Security Session에 User정보 저장
    }
}
