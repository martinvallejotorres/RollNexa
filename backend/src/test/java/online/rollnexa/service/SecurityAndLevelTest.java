package online.rollnexa.service;
import org.junit.jupiter.api.Test; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import static org.assertj.core.api.Assertions.*;
class SecurityAndLevelTest {
 @Test void bcryptNeverStoresPlainPassword(){var e=new BCryptPasswordEncoder(12);String hash=e.encode("Rollnexa123!");assertThat(hash).isNotEqualTo("Rollnexa123!");assertThat(e.matches("Rollnexa123!",hash)).isTrue();}
 @Test void levelFormulaIsDeterministic(){var s=new LevelService();assertThat(s.levelFor(0)).isEqualTo(1);assertThat(s.levelFor(900)).isEqualTo(4);assertThat(s.xpForLevel(4)).isEqualTo(1600);}
}

