package donaciones.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenBlacklistTest {

    @Test
    void blacklistToken_addsTokenToBlacklist() {
        TokenBlacklist blacklist = new TokenBlacklist();
        String token = "testToken";
        blacklist.blacklistToken(token);
        assertTrue(blacklist.isTokenBlacklisted(token));
    }

    @Test
    void isTokenBlacklisted_returnsTrueIfTokenIsBlacklisted() {
        TokenBlacklist blacklist = new TokenBlacklist();
        String token = "testToken";
        blacklist.blacklistToken(token);
        assertTrue(blacklist.isTokenBlacklisted(token));
    }

    @Test
    void isTokenBlacklisted_returnsFalseIfTokenIsNotBlacklisted() {
        TokenBlacklist blacklist = new TokenBlacklist();
        String token = "testToken";
        assertFalse(blacklist.isTokenBlacklisted(token));
    }

    @Test
    void blacklistToken_multipleTokens() {
        TokenBlacklist blacklist = new TokenBlacklist();
        String token1 = "token1";
        String token2 = "token2";
        blacklist.blacklistToken(token1);
        blacklist.blacklistToken(token2);
        assertTrue(blacklist.isTokenBlacklisted(token1));
        assertTrue(blacklist.isTokenBlacklisted(token2));
    }

    @Test
    void isTokenBlacklisted_emptyBlacklist() {
        TokenBlacklist blacklist = new TokenBlacklist();
        String token = "testToken";
        assertFalse(blacklist.isTokenBlacklisted(token));
    }
}