package gameservice;

import java.awt.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Constants {
    public static final int fps = 60;//For Dbg
    //public static final int fps = 60;
    public static final float gravAcc = 0.15f;// a: speed per frame
    public static final String imageMenuBGFileName = "resources/Image/imageMenuBG2.png";
    public static final String imageDialogBGFileName = "resources/Image/imageDialogBG.png";
    public static final String imagePauseMenuBGFileName = "resources/Image/imagePauseMenuBG.png";
    public static final String imageBGFileName = "resources/Image/imageBG320.568.png";
    public static final String imageGameOverBGFileName = "resources/Image/imageGameOverBG.png";
    public static final String imageIconFileName = "resources/Image/imageIcon.png";
public static final String textHelp="<html><body><h2 id='introduction'><span>Introduction</span></h2><p><span>An alien civilization is invading the human world. All the countries set up bombs which can be ejected between the</span>\n" +
        "<span>spacecraft to defend the aliens. However, the enemy build an artificial black hole to devour our bombs.</span></p><p><span>Let&#39;s protect our motherland against endless enemies.</span></p><h2 id='how-to-play'><span>How to play</span></h2><p><span>Click any position to set up bombs. The spacecrafts can withstand several attacks. Every turn they appear and move towards you. When they reach the earth, you lose. There are also four kinds of special targets with different effects when you destroy them:</span></p><p><span>B: the bomb will destroy all the targets in your sight.</span></p><p><span>T: the time machine will make all targets turn back by two actions.</span></p><p><span>+: the increasing target will give you a bonus ball permanently.</span></p><p><span>*: the enhancing target will make your balls takes more damage next time.</span></p></body></html>";
    public static final float lineWidthDiv2 = 6.0F;
    public static final int panelWidth = 320;
    public static final int panelHeight = 568;
    public static final int targetNumPerLevel = 5;
    public static final float circleRadius = 20f;
    public static final float rectRadius = 25f;//使面积一致
    public static final float triRadius = 31f;//使面积一致
    public static final float paintBallRadiusBias = 3.0f;//给Ball绘图时的半径修正，使碰撞效果看上去更真实
    public static final float distanceToBottom = 42.0f;
    public static final float distanceToSide = 36.0f;
    public static final float distanceBalls = 55.0f;
    public static final float distanceLevels = 55.0f;
    public static final float ballOriginY = 48.5f;
    public static final float scoreTextX = 9.0f;
    public static final float scoreTextY = 36.0f;
    public static final String audioCollideFileName = "resources/Audio/collide.mp3";
    public static final float PI = (float) Math.PI;
    public static final float sqrt2 = (float) Math.sqrt(2.0);
    public static final float sqrt3 = (float) Math.sqrt(3.0);
    public static final float speedLoss = 0.8f;//碰撞时的垂直法线速度损失
    public static final Font hitFont = new Font("Tekton Pro", Font.BOLD, 15);
    public static final int rankNum = 5;
    static final float originalSpeed = 4.0f;// speed: px per frame
    static final float ballRadius = 5.0f;
    static final int ballDelay = 12;
    static final String audioBGMFileName = "resources/Audio/ra2motorize.mp3";
    static final String audioGameOverFileName = "resources/Audio/GameOver.mp3";
    public static Lock lock = new ReentrantLock();//对Game数据的锁，在Game类和GamePanel类中使用
    public static final String rankFileName = "rank";
    public static final float bombRate=0.01f;
    public static final float timeMachineRate=0.01f;
    public static final float ballEnhanceRate=0.03f;
    public static final float ballIncreaseRate=0.02f;

}
