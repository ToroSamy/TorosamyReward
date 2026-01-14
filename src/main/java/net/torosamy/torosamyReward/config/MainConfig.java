package net.torosamy.torosamyReward.config;

import net.torosamy.torosamyCore.config.IConfigManage;

import java.util.List;

public class MainConfig implements IConfigManage {
    public FirstJoin firstJoin = new FirstJoin();
    public class FirstJoin implements IConfigManage {
        public Boolean enabled;
        public List<String> actions;

        public WelcomeReward welcomeReward = new WelcomeReward();
        public class WelcomeReward implements IConfigManage {
            public Boolean enabled;
            public Integer time;
            public List<String> actions;
            public List<String> keys;
        }
    }
    public RewardBoxDefaultItem rewardBoxDefaultItem = new RewardBoxDefaultItem();
    public class RewardBoxDefaultItem implements IConfigManage {
        public List<Integer> slots;
    }

    public TimeCommand timeCommand = new TimeCommand();
    public class TimeCommand implements IConfigManage {
        public Boolean enabled;
    }
}
