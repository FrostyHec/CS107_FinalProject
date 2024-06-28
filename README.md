<div align=center>

# Dark Chess: CS107 Final Project

[中文](#项目介绍) /
[English](#project-introduction)

南方科技大学2022秋季 `CS107 计算机程序设计基础(H)`期末大作业：**中国暗棋**

Southern University of Science and Technology, Autumn 2022
`CS107 Introduction to Computer Programming (H)` Final Project: **Dark Chess**

支持多种等级的人机对战与联网游戏的中国暗棋游戏，同时包含动画、音效、积分榜、自定义设置等多种功能，使用说明请见 [使用文档](Publication/UserGuide.md)

A Chinese dark chess game that supports man-machine battles and online games with multiple levels,
while also including animations, sound effects, leaderboards, custom settings, and other features.
For instructions, please refer to [User Guide](Publication/UserGuide.md)

开发列表 / Developers :  [@Frosky Lrupotkin](https://github.com/FrostyHec) | [@Super](https://github.com/redhecker)

得分 / Score : 110/100 (10 bonus)

**Enjoy the game!**

![img.png](Publication%2FPic%2Fimg.png)

</div>

## Project Introduction

### Project Structure

```
DarkChess
├── Code/DarkChess              # source code
├── Docs             
│   ├── developing              # project design document
│   ├── Reference               # referencing materials 
│   │                            (project requirement in previous semester)
│   └── Demostrate              # presentation materials
├── Publication  
│   ├── DarkChess               # Executable file of the application               
│   └── UserGuide.md            
├── .gitattributes
├── .gitignore
├── LICENSE
└── README.md
```

### Feature List and Technical Implementation

The project implement the following functionalities from scratch:

- [x] Game Logic
    - [x] Random Initialization
    - [x] Move Legality Check
    - [x] Score Calculation
    - [x] Multi-step Undo
    - [x] Cheat Mode
    - [x] Game Progress Detection and Hints
    - [x] Restart Between Games
- [x] Game Storage (Technical Implementation: Serialization)
    - [x] State Saving
    - [x] Save File Legality Check
    - [x] Save File Replay
    - [x] Load Specified Save File
    - [x] Save File Deletion
    - [x] Import External Save Files
- [x] Graphical Interface (Technical Implementation: JavaFX+CSS)
    - [x] Basic Interface Drawing
    - [x] Game Hints (e.g., Highlight Selected Piece, Move Hints)
    - [x] Multi-interface Switching
    - [x] Animation Effects
    - [x] Variable Screen Styles
    - [x] Screenshots
- [x] Artificial Intelligence
    - [x] Game Mode Switching (Support for Human vs. AI)
    - [x] Random AI
    - [x] Basic Greedy AI
    - [x] MinMax Search AI
    - [x] Multiple AI Difficulty Levels
- [x] Multi-user
    - [x] Local Multi-user
    - [x] Game Record Statistics
    - [x] Leaderboard
    - [x] Custom User Information (e.g., Avatar)
- [x] Online Battle (Technical Implementation: Native Java Socket)
    - [x] Server Side
    - [x] Client Side
    - [x] LAN Multiplayer
    - [x] Multiplayer Game Rooms
    - [x] Spectator Mode
    - [x] Gameplay Data Statistics
- [x] Sound Effects
    - [x] Button Sound Effects
    - [x] Background Music
    - [x] Background Music Switching Based on Game Progress
    - [x] Expandable Music Pack
    - [x] Music Station (Switchable Music)
- [x] Multi-language
    - [x] Multi-language Support
    - [x] Expandable Language Pack
- [x] Settings
    - [x] Default Function Selection
    - [x] Game Function Settings
    - [x] Adjustable Visual Effects
    - [x] Adjustable Sound Effects
    - [x] In-game Settings Modification (Partial Effect)
- [x] Other Features
- [x] EXE Packaging
- [x] Early Defense

(Note: The above list only includes the main basic features. For detailed instructions and usage, see
the [User Guide](Publication/UserGuide.md))

## Development Summary

### Development Log

- 2022.11.14 - 2022.12.20 Approximately 5 weeks

- 2022.11.14 - 2022.11.20 Project framework design, basic game logic, and graphical interface implementation
- 2022.11.21 - 2022.11.27 Game logic debugging, further improvement of the graphical interface (scoreboard, user bar),
  cheat function, game restart, main menu, captured pieces display
- 2022.11.28 - 2022.12.04 Save function implementation, undo function implementation, game debugging
- 2022.12.05 - 2022.12.11 Artificial intelligence implementation, multi-user implementation, music station
  implementation, game mode selection, online battle development
- 2022.12.12 - 2022.12.18 Online battle debugging, leaderboard, game information statistics (e.g., win rate, game
  duration), interface beautification, piece animation settings implementation
- 2022.12.19 - 2022.12.20 Screenshot function implementation, expansion of language packs, music packs, and skin packs,
  music player debugging completion, EXE packaging, defense materials preparation
- 2022.12.20 Early Presentation

Cumulative code: Approximately 8000 lines of Java + Fxml + Css + resource texts
![Java+Fxml+Css approach to 9300lines.png](Docs%2Fdeveloping%2FJava%2BFxml%2BCss%20approach%20to%209300lines.png)

263 Commit
![263 commits.png](Docs%2Fdeveloping%2F263%20commits.png)

### Technical Summary

Graphical Interface: Honestly, developing desktop applications with JavaFX is much better than Swing. HTML is very
suitable for describing graphical interfaces, and CSS can easily implement theme switching (The project's
technology choices were limited, otherwise, using Electron might have resulted in a better visual interface).

Framework Design: Proper use of object-oriented principles and some design patterns can balance simplicity and
extensibility. Both design and coding are very important.

Network Design: There are indeed many exceptions and special states that need to be handled in network design. It is
essential to first sort out the possible states during network interaction and determine specific handling methods
before starting to write code. During development, we encountered the "packet sticking problem". After studying
computer networks, we better understood the stream transmission characteristics of TCP, and realized the need to add
corresponding headers/footers for segmentation to solve this "problem".

Others: The development of many small features was actually quite fast (it seems that a lot of functional blocks were
completed in one week according to the log), but thorough testing (preferably writing automated test cases) is required
to ensure the quality of the program.

### Postscript

This is probably the first time I have implemented a project with so many features from scratch. Many thanks to my
partner [@Super](https://github.com/redhecker) for working together to complete this project.

I didn't expect to accomplish so much in five weeks. It was a lot of work, but it paid off in the end.

The reference scoring standards from previous years were very detailed, essentially guiding the project's requirements (
clear requirements seem to be the most critical part of the development process, and having this reference was quite
convenient).

Although the final product still has many shortcomings, we will continue to work hard and hope to do better in future
projects. 

We hope you enjoy your time with this game!

—— Frosty

## 项目介绍

### 项目结构

```
DarkChess
├── Code/DarkChess              # 项目源代码
├── Docs             
│   ├── developing              # 部分项目设计文档
│   ├── Reference               # 其它学期的参考资料
│   └── Demostrate              # 答辩所展示的资料文件
├── Publication  
│   ├── DarkChess               # 内含exe可执行文件               
│   └── UserGuide.md            
├── .gitattributes
├── .gitignore
├── LICENSE
└── README.md
```

### 功能列表与技术实现

本项目从空项目开始，实现了以下功能：

- [x] 游戏逻辑
    - [x] 随机初始化
    - [x] 行棋合法性判断
    - [x] 积分计算
    - [x] 多步悔棋
    - [x] 作弊模式
    - [x] 游戏进度探测与提示
    - [x] 游戏间重新开始
- [x] 游戏存储 (技术实现: 序列化)
    - [x] 状态保存
    - [x] 存档合法性检查
    - [x] 存档回放
    - [x] 指定存档载入
    - [x] 存档删除
    - [x] 外部存档导入
- [x] 图形化界面 (技术实现: JavaFX+CSS)
    - [x] 基本界面绘制
    - [x] 游戏提示(如选中棋子高亮,移动提示)
    - [x] 多界面切换
    - [x] 动画效果
    - [x] 可变画面风格
    - [x] 截图
- [x] 人工智能
    - [x] 游戏模式切换(人机对战支持)
    - [x] 随机AI
    - [x] 基本贪心AI
    - [x] MinMax搜索AI
    - [x] 多AI难度选择
- [x] 多用户
    - [x] 本地多用户
    - [x] 游戏记录统计
    - [x] 积分榜
    - [x] 自定义用户信息(如头像)
- [x] 网络对战 (技术实现: Java原生Socket)
    - [x] 服务器端
    - [x] 客户端
    - [x] LAN联机
    - [x] 多人游戏房间
    - [x] 观战模式
    - [x] 游玩数据统计
- [x] 音效
    - [x] 按键音效
    - [x] 背景音乐
    - [x] 依据游戏进程的背景音乐切换
    - [x] 可扩展音乐包
    - [x] 音乐台(可切换音乐)
- [x] 多语言
    - [x] 多语言支持
    - [x] 可扩展语言包
- [x] 设置
    - [x] 默认功能选择
    - [x] 游戏功能设置
    - [x] 可调节视觉效果
    - [x] 可调节音效
    - [x] 游戏期间设置修改(局部生效)
- [x] 其它功能
- [x] EXE 打包
- [x] 提前答辩

(注:  上述列表仅包含主要基本功能，详细说明与使用参见 [使用文档](Publication/UserGuide.md))

## 开发总结

### 开发日志

- 2022.11.14 - 2022.12.20 约5周

- 2022.11.14 - 2022.11.20 项目框架设计、基本游戏逻辑和图形化界面实现
- 2022.11.21 - 2022.11.27 游戏逻辑调试、游戏图形化界面进一步完善(计分板，用户栏)，作弊功能、游戏重启、主菜单、被吃子显示
- 2022.11.28 - 2022.12.04 存档功能实现、悔棋实现、游戏调试
- 2022.12.05 - 2022.12.11 人工智能实现、多用户实现、音乐台实现、游戏模式选择、网络对战开发
- 2022.12.12 - 2022.12.18 网络对战调试、积分榜、游戏信息统计(如胜率，游戏时长)、界面美化、棋子动画设置功能实现
- 2022.12.19 - 2022.12.20 截图功能实现、多语言包、音乐包与皮肤包扩充、音乐播放器完成调试、EXE打包、答辩资料准备
- 2022.12.20 提前答辩(答辩前五分钟还在代码冲刺(x))

累计代码：Java+Fxml+Css+资源文本总计约8000行
![Java+Fxml+Css approach to 9300lines.png](Docs%2Fdeveloping%2FJava%2BFxml%2BCss%20approach%20to%209300lines.png)

Commit: 263次
![263 commits.png](Docs%2Fdeveloping%2F263%20commits.png)

### 技术总结

图形化界面：说实在做桌面JavaFX还是比Swing来的要好用的，HTML很适合用于描述图形化界面，CSS也能很方便地实现
主题风格的切换（不过主要是project对技术选型有限制，否则用Electron可能能够做出效果更好的视觉界面）

框架设计：用好面向对象以及一些设计模式能够平衡好简洁与可扩展性，设计和编码都是很重要的

网络设计：网络要处理的异常与特殊状态之类的真挺多的，还是要先梳理网络交互过程中的可能状态以及确定具体处理方式
再开始写代码。在开发过程中遇到了“粘包问题”，但其实学了计网之后才更加清晰的理解了TCP的流式传输特性，需要添加
对应的包头/尾以进行分割

其它：许多小功能的开发其实速度还是挺快的（似乎log里面有一周速通了很多功能块），不过还是需要充分的测试（最好是
编写可自动化运行的测试用例）才能保证程序的质量

### 后记

应该是第一次从零开始实现了包含了这么多功能的项目，十分感谢我的搭档[@Super](https://github.com/redhecker)能够一起完成这个项目。

没想到五周时间能做出这么多东西（虽然其实很多工作都是在中后期开始一天实现的一个功能（（

Reference的往年评分标准非常详细，基本上就给project实现什么需求指定了一个方向 (感觉明确需求反而可能是开发过程中最重要的一环，
project有这个参考挺省事的((x

虽然最后的成品还有很多不足，但是我们会继续努力，希望能够在未来的项目中做得更好。希望您能够在这个游戏中度过愉快的时光！

—— Frosty


