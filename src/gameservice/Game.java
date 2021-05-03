package gameservice;

import items.*;
import ui.Frame;

import java.util.*;

/**
 * 该类实现游戏内所有元素的实际行为
 *
 * @author Hu Yongkang
 */
public class Game {

    static private final int Status_ready = 0;
    static private final int Status_run = 1;
    static private final int Status_pause = 2;//暂停状态
    static private final int Status_prepare = 3;//准备新的targets
    static private final int Status_Over = 4;
    static int level = 0;
    private static int ballNum=1;
    static private List<Ball> balls;
    static private List<Targets> targets;
    private static int score = 0;
    private static int status = Status_prepare;
    private static int statusBeforePause = Status_ready;
    private static boolean isEnhancedNext=false;
    private static int levelsHidden=0;
    private static boolean isBomb=false;
    private static boolean isTimeMachine=false;
    Random random = new Random();

    public Game() {
        balls = new LinkedList<>();
        targets = new LinkedList<>();
    }

    public static void reset() {
        setBallNum(1);
        level = 0;
        score = 0;
        status = Status_prepare;
        statusBeforePause = Status_ready;
        balls.clear();
        targets.clear();
    }

    /**
     * 将game暂停,保留暂停前状态直到继续
     *
     * @see #goOn()
     * @see #statusBeforePause
     */
    public static void pause() {
        Constants.lock.lock();
        if (status != Status_pause) statusBeforePause = status;
        status = Status_pause;
        Constants.lock.unlock();
    }

    /**
     * 将暂停的game继续
     */
    static public void goOn() {
        Constants.lock.lock();
        status = statusBeforePause;
        Constants.lock.unlock();
    }

    public static List<Ball> getBalls() {
        return balls;
    }

    public static List<Targets> getTargets() {
        return targets;
    }

    public static int getScore() {
        return score;
    }

    public static int getBallNum() {
        return ballNum;
    }

    public static void setBallNum(int ballNum) {
        Game.ballNum = ballNum;
    }

    /**
     * 根据不同的Status进行处理，并更新Status。除该方法外，其它方法改变status:
     * {@link Game#goOn()}
     * {@link #pause()}
     * {@link Game#setNextMouseLoc(Location)}
     */
    public void run() {
        Constants.lock.lock();
        if (status == Status_pause)
            ;//do nothing
        else if (status == Status_prepare) {//旧Targets上移，新Targets准备，游戏结束判断。
            level++;
            if(isTimeMachine)_timeMachine();
            for (Targets target : getTargets()) {//旧Target上移,若为被timeMachine影响的hidden状态则多上移10格
                float _Y=target.getLocation().getY()-Constants.distanceLevels;
                float YHidden=Constants.panelHeight-Constants.distanceToBottom+10*Constants.distanceLevels;
                if(_Y>YHidden-1&&_Y<YHidden+1)_Y-=10*Constants.distanceLevels;
                target.getLocation().set(target.getLocation().getX(), _Y);
            }
            for (Targets target : getTargets()) {//判断游戏是否结束
                if (target.getLocation().getY() < Constants.ballOriginY) {
                    status = Status_Over;
                    break;
                }
            }
            if(levelsHidden==0){
                for (int i = 0; i < Constants.targetNumPerLevel; i++) {
                    Location nextLocation = new Location(
                            Constants.distanceToSide + ((level % 2) / 2.0f + i) * Constants.distanceBalls,
                            Constants.panelHeight - Constants.distanceToBottom);
                    int nextHitPoints = random.nextInt(5 + 2 * level) + 1;//TODO 合理的难度设置
                    float nextAngle = 2 * Constants.PI * random.nextFloat();
                    float randFloat=random.nextFloat();
                    if((randFloat-=Constants.bombRate)<0){//按几率生成特殊道具
                        getTargets().add(new Bomb(nextLocation,Constants.circleRadius));
                    }else if((randFloat-=Constants.timeMachineRate)<0){
                        getTargets().add(new TimeMachine(nextLocation,Constants.circleRadius));
                    }else if((randFloat-=Constants.ballIncreaseRate)<0){
                        getTargets().add(new BallIncrease(nextLocation,Constants.circleRadius));
                    }else if(randFloat - Constants.ballEnhanceRate <0){
                        getTargets().add(new BallEnhance(nextLocation,Constants.circleRadius));
                    }else
                    switch (random.nextInt(5)) {//随机选择几何形状
                        case 4:
                        case 0: //no target here, but at least one target in center
                            if (i != 3) break;
                        case 1:
                            getTargets().add(new Circle(nextHitPoints, nextLocation, Constants.circleRadius));
                            break;
                        case 2:
                            getTargets().add(new Rectangle(nextHitPoints, nextLocation, Constants.rectRadius, nextAngle));
                            break;
                        case 3:
                            getTargets().add(new Triangle(nextHitPoints, nextLocation, Constants.triRadius, nextAngle));
                            break;
                    }
                }
            }else levelsHidden--;
            if (status != Status_Over)//若游戏不结束，等待下一个鼠标点击
                status = Status_ready;
        } else if (status == Status_ready) {//等待鼠标click事件
        } else if (status == Status_run) { //bomb道具生效、元素运动、相交检测
            if(isBomb)_bomb();
            if (getBalls().isEmpty()) {
                status = Status_prepare;
            } else{
                for (Iterator<Ball> ballIterator = getBalls().iterator(); ballIterator.hasNext(); ) {//TODO 使用更高效的算法求交和运动
                    Ball ball = ballIterator.next();
                    if (ball.isDelay()) ball.delay();//发球等待
                    else {
                        ball.move();
                        if (interactEdge(ball)) ballIterator.remove();//从下方飞出
                        for (Iterator<Targets> targetsIterator = getTargets().iterator(); targetsIterator.hasNext(); ) {//目前是对每个target遍历求交
                            Targets target = targetsIterator.next();
                            if (target.interactBalls(ball)) {
                                Audio.Collide.play();
                                score = getScore() + 1;
                                if (target.hit())
                                    targetsIterator.remove();
                                else if(ball.isEnhanced())//若hitPoints>1, ball is enhanced, 再进行一次碰撞
                                    if (target.hit())
                                    targetsIterator.remove();
                            }
                        }
                    }
                }

            }
        } else {//status==Status_Over
            //在结束处停留一秒，播放音效，进入结束界面
            Audio.GameOver.play();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Frame.showGameOver(score);
        }
        Constants.lock.unlock();
    }

    /**
     * 获取下一个弹球发射位置，准备球，开始发球
     *
     * @return 如果在正确的状态 (status_ready) 获取了正确范围内的位置，返回真
     */
    public boolean setNextMouseLoc(Location location) {
        Constants.lock.lock();
        Location originLocation = new Location(Constants.panelWidth / 2.0f, Constants.ballOriginY);
        float X = location.getX(), Y = location.getY();
        if ((status == Status_ready) && (X < Constants.panelWidth && X > 0 && Y < Constants.panelHeight && Y > Constants.ballOriginY + 10.0f)) {
            status = Status_run;
            /* prepare for balls */
            for (int i = 0; i < getBallNum(); i++) {
                getBalls().add(new Ball(Constants.ballRadius, Constants.originalSpeed,
                        (float) Math.atan2(location.getY() - originLocation.getY(), location.getX() - originLocation.getX()),
                        originLocation, i * Constants.ballDelay,isEnhancedNext));
            }
            isEnhancedNext=false;
            if(level%3==1) setBallNum(getBallNum() + 1);
            Constants.lock.unlock();
            return true;
        } else {
            Constants.lock.unlock();
            return false;
        }
    }

    /**
     * 判定与界面边缘相交，并改变运动方向,若离开底部，返回true
     *
     * @param ball 判断小球
     * @return 小球是否离开范围从下方飞出
     */
    boolean interactEdge(Ball ball) {
        float X = ball.getLocation().getX(), Y = ball.getLocation().getY();
        if (X <= 0) {
            ball.getLocation().set(-X, Y);
            ball.reflect(0);
        } else if (X >= Constants.panelWidth) {
            ball.getLocation().set(2.0f * Constants.panelWidth - X, Y);
            ball.reflect(0);
        }
        if (Y <= 0) {
            ball.getLocation().set(X, -Y);
            ball.reflect(Constants.PI / 2.0f);
        } else if (Y >= Constants.panelHeight) {
            return true;
        }
        return false;
    }
    static public void enhanceNext(){
        isEnhancedNext=true;
    }
    static public void timeMachine(){
        isTimeMachine=true;
    }
    private void _timeMachine(){
        isTimeMachine=false;
        for (Targets target : targets) {
            float _Y=target.getLocation().getY()+2*Constants.distanceLevels;
            if(_Y>Constants.panelHeight-Constants.distanceToBottom+1)_Y+=10*Constants.distanceLevels;//防止target边缘影响小球判定，在prepare状态伺机还原
            target.getLocation().set(target.getLocation().getX(),_Y);
        }
        levelsHidden+=2;
    }
    static public void bomb(){
        isBomb=true;
        }
    private void _bomb(){
        isBomb=false;
        targets.removeIf(targets -> targets.getLocation().getY() < Constants.panelHeight);
    }
}
