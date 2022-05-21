package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.LoginUser.Data.DTO.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        return
                userRepository.findByNickName(userId)
                        .filter(m -> m != null)
                        .map(m -> new SecurityUser(m)).get();
    }
}