package com.gamul.gamul.api.auth.service;

import com.gamul.gamul.api.auth.domain.dto.*;
import com.gamul.gamul.api.auth.jwt.TokenProvider;
import com.gamul.gamul.domain.entity.Member;
import com.gamul.gamul.domain.entity.RefreshToken;
import com.gamul.gamul.exception.DuplicateEmailException;
import com.gamul.gamul.repository.MemberRepository;
import com.gamul.gamul.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new DuplicateEmailException();
        }

        Member member = signUpRequestDto.toMember(passwordEncoder);
        return SignUpResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String email = loginRequestDto.getEmail();
        String name = memberRepository.findByEmail(email).get().getName();

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        LoginResponseDto loginResponseDto = tokenProvider.generateTokenDto(authentication,name,email);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(loginResponseDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return loginResponseDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        Member member = memberRepository.findById(Long.parseLong(authentication.getName())).get();

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication, member.getEmail());

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

}
