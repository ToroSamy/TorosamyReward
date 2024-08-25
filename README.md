## Dependency
- Residence
- TorosamyCore
## Function
- Get rewards when players welcome new players
- Custom timing instruction group allows for customization of online rewards


## Usage
1. download [TorosamyCore](https://github.com/ToroSamy/TorosamyCore) and [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) as a dependency plugin
2. put the **dependencies** and this plugin into your plugins folder and start your server
3. Modify the default configuration file generated and restart your server
## Permission
- - **Usage:** /tr reload
  - **Description:** reload this plugin
  - **Permission:** torosamyReward.reload


## Config

### config.yml
```yml
first-join:
  enabled: true
  #[console] [allMessage] [message] [player]
  actions: []
  welcome-reward:
    enabled: true
    #新玩家进服，其他玩家在多少时间内欢迎才可获得奖励
    time: 30
    #[console] [allMessage] [message] [player]
    actions: []
    #欢迎关键词，包含这些就可以获得奖励
    keys:
      - "欢迎"
time-command:
  enabled: true
```

### lang.yml
```yml
reload-message: "&b[服务器娘]&a插件 &TorosamyReward &a重载成功喵~"
```

### template time-command
```yml
daily-online-reward:
  permission: ""
  time: 300
  #[op] [allMessage] [player] [permission]
  commands:
    - "[console] say hello"
    - "[message] &bmessage"
```

## Contact Author

- qq: 1364596766
- website: https://www.torosamy.net

## License

[GPL-3.0 license](./LICENSE)
