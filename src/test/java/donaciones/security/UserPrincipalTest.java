package donaciones.security;

import donaciones.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserPrincipalTest {

    @Test
    void getAuthorities_isAdmin() {
        Usuario usuario = new Usuario();
        usuario.setIsAdmin(true);
        UserPrincipal userPrincipal = new UserPrincipal(usuario);
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void getAuthorities_isOrgOwner() {
        Usuario usuario = new Usuario();
        usuario.setIsOrgOwner(true);
        UserPrincipal userPrincipal = new UserPrincipal(usuario);
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ORG_OWNER")));
    }

    @Test
    void getAuthorities_defaultRole() {
        Usuario usuario = new Usuario();
        UserPrincipal userPrincipal = new UserPrincipal(usuario);
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void getUsername() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        UserPrincipal userPrincipal = new UserPrincipal(usuario);
        assertEquals("test@example.com", userPrincipal.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        Usuario usuario = new Usuario();
        UserPrincipal userPrincipal = new UserPrincipal(usuario);
        assertTrue(userPrincipal.isAccountNonExpired());
    }
}