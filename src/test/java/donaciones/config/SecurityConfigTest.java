package donaciones.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    void testSecurityFilterChainBeanCreation() {
        assertThat(context.getBean(SecurityFilterChain.class)).isNotNull();
    }

    @Test
    void testCorsConfigurationSourceBeanCreation() {
        // Use the specific bean name you want to test
        assertThat(context.getBean("corsConfigurationSource", CorsConfigurationSource.class)).isNotNull();
    }

    @Test
    void testAuthenticationManagerBeanCreation() {
        assertThat(context.getBean(AuthenticationManager.class)).isNotNull();
    }

    @Test
    void testPasswordEncoderBeanCreation() {
        assertThat(context.getBean(PasswordEncoder.class)).isNotNull();
    }
}