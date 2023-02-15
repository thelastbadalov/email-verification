package az.company.emailverification.appuser;

import az.company.emailverification.registration.token.ConfirmationToken;
import az.company.emailverification.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
private final AppUserRepository repository;
private final BCryptPasswordEncoder passwordEncoder;
private final ConfirmationTokenService service;
private final static String USER_NOT_FOUND_MSG="user with email %s not found";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return  repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException
                (String.format(USER_NOT_FOUND_MSG,email)));

    }
    public String signUpUser(AppUser appUser){
        boolean userExist = repository.findByEmail(appUser.getEmail()).isPresent();

        if(userExist){
            throw  new IllegalStateException("email already taken");

        }
String encode=passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encode);

        repository.save(appUser);
String token= UUID.randomUUID().toString();
        ConfirmationToken confirmationToken=new ConfirmationToken(
                token,appUser, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15)
        );
        service.saveConfirmation(confirmationToken);
return token;
    }

    public int enableAppUser(String email) {
    return repository.enableAppUser(email);
    }
}
