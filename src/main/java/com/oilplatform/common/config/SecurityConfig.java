package com.oilplatform.common.config;

import com.oilplatform.common.utils.JwtUtils;
import com.oilplatform.common.utils.MD5Utils;
import com.oilplatform.modules.user.entity.User;
import com.oilplatform.modules.user.mapper.UserMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // 开启方法级权限控制（@PreAuthorize等）
public class SecurityConfig {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    //UserDetailsService：从数据库加载用户信息
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userMapper.selectByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            // 根据角色赋予权限，这里简单处理：role_id 对应角色，可扩展为从 role 表读取
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (user.getRoleId() == 1) {
                authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
            } else if (user.getRoleId() == 2) {
                authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
            } else if (user.getRoleId() == 3) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            // 返回Spring Security的User对象，密码字段已加密
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getStatus() == 1, // enabled
                    true, true, true,
                    authorities
            );
        };
    }

    //密码编码器：使用MD5 + 固定盐值，与原有加密方式一致
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Utils.encrypt(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return MD5Utils.verify(rawPassword.toString(), encodedPassword);
            }
        };
    }

    //认证提供者：结合 MD5 密码验证和用户状态检查

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();

                User user = userMapper.selectByUsername(username);
                if (user == null) {
                    throw new BadCredentialsException("用户名或密码错误");
                }
                if (user.getStatus() == 0) {
                    throw new BadCredentialsException("账号已被禁用");
                }
                if (!MD5Utils.verify(password, user.getPassword())) {
                    throw new BadCredentialsException("用户名或密码错误");
                }

                // 构建权限集合
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                if (user.getRoleId() == 1) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
                } else if (user.getRoleId() == 2) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
                } else if (user.getRoleId() == 3) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }

                return new UsernamePasswordAuthenticationToken(
                        user.getUserId(), // principal 改为 userId，方便后续使用
                        null,
                        authorities
                );
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    //JWT 过滤器：从请求头提取 Token 并验证，设置安全上下文

    @Bean
    public OncePerRequestFilter jwtAuthenticationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                // 从请求头获取 Token
                String token = request.getHeader("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    try {
                        if (!jwtUtils.isTokenExpired(token)) {
                            Long userId = jwtUtils.getUserId(token);
                            String username = jwtUtils.parseToken(token).getSubject();
                            Integer roleId = jwtUtils.getRoleId(token);

                            // 根据 userId 构建 Authentication（不查数据库，减少开销）
                            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                            if (roleId == 1) {
                                authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
                            } else if (roleId == 2) {
                                authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
                            } else if (roleId == 3) {
                                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                            }

                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
                            // 可设置 details，例如 IP
                            authentication.setDetails(request);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } catch (Exception e) {
                        logger.error("JWT 校验失败", e);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    //核心安全配置

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF（前后端分离 + JWT 通常关闭）
                .csrf(csrf -> csrf.disable())
                // 无状态会话
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 请求授权规则
                .authorizeHttpRequests(auth -> auth
                        // 放行登录、注册等公开接口
                        .requestMatchers("/api/user/login", "/api/user/register").permitAll()
                        // 学生权限接口
                        .requestMatchers("/api/course/enroll/**", "/api/report/submit", "/api/game/submit").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        // 教师权限接口
                        .requestMatchers("/api/course/create", "/api/report/unreviewed", "/api/report/review/**").hasAnyRole("TEACHER", "ADMIN")
                        // 管理员权限接口
                        .requestMatchers("/api/admin/**", "/api/system/**").hasRole("ADMIN")
                        // 其余接口需要认证
                        .anyRequest().authenticated()
                )
                // 添加 JWT 过滤器，在 UsernamePasswordAuthenticationFilter 之前执行
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 配置自定义认证提供者
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}