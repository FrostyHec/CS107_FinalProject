

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

The project implement the following functionalities from scratch:


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

累计代码：Java+Fxml+Css总计约9300行
![Java+Fxml+Css approach to 9300lines.png](Docs%2Fdeveloping%2FJava%2BFxml%2BCss%20approach%20to%209300lines.png)

Commit: 263次
![263 commits.png](Docs%2Fdeveloping%2F263%20commits.png)

### 问题与总结

[TODO]

### 后记

应该是第一次从零开始实现了包含了这么多功能的项目，十分感谢我的搭档[@Super](https://github.com/redhecker)能够一起完成这个项目。

虽然最后的成品还有很多不足，但是我们会继续努力，希望能够在未来的项目中做得更好。希望您能够在这个游戏中度过愉快的时光！

—— Frosty

Finally finished in 2022.12.20 last about 5 week.

This's our JavaA final project finished in 5 weeks. Since we are just the freshmen to programming and lack of the
methods of Software Engineering also time, it's now a half-finished demo but can run most of the functions. We will
continue to improve our skills and ability. Hope you can enjoy your time in this game!


