package net.torosamy.torosamyReward.config;

import net.torosamy.torosamyCore.config.TorosamyConfig;
import net.torosamy.torosamyReward.pojo.TimeCommand;

import java.util.List;

public class MainConfig extends TorosamyConfig {
    public FirstJoin firstJoin = new FirstJoin();
    public class FirstJoin extends TorosamyConfig {
        public Boolean enabled;
        public List<String> actions;

        public WelcomeReward welcomeReward = new WelcomeReward();
        public class WelcomeReward extends TorosamyConfig {
            public Boolean enabled;
            public Integer time;
            public List<String> actions;
            public List<String> keys;
        }
    }


    public TimeCommand timeCommand = new TimeCommand();
    public class TimeCommand extends TorosamyConfig {
        public Boolean enabled;
    }
}
