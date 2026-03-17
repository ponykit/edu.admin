package com.edu.admin.service;


import com.edu.admin.dao.AdminDao;
import com.edu.admin.model.security.AdminUser;
import com.edu.admin.model.security.Role;
import com.edu.admin.model.security.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    public AdminUser getUser() throws UsernameNotFoundException { 
        AdminUser user = (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public int joinUser(Map<String, Object> userInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfo.put("password", passwordEncoder.encode(userInfo.get("password").toString()));

        return adminDao.addAdminUser(userInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("admId", adminId);

        UserDto adminInfo  = adminDao.selectAdmin(params);

        if(adminInfo != null) {

            List<GrantedAuthority> authorities = new ArrayList<>();

            if (("admin").equals(adminId)) {
                authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
            }

            AdminUser user = new AdminUser(adminInfo.getAdmId(), adminInfo.getAdminSeq(), adminInfo.getAdmName(), adminInfo.getAdmPwd(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            try {
                user.setAdmEmail(adminInfo.getAdmEmail());

            } catch(Exception e) {
                e.printStackTrace();
            }

            return user;


        } else {

            throw new UsernameNotFoundException(adminId);

        }

    }

}