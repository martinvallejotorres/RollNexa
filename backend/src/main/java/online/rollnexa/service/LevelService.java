package online.rollnexa.service;
import org.springframework.stereotype.Service;
@Service public class LevelService {
 public long xpForLevel(int level){return 100L*level*level;}
 public int levelFor(long xp){return Math.max(1,(int)Math.floor(Math.sqrt(xp/100.0))+1);}
}

