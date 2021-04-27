package gameservice;

import items.Rectangle;
import items.*;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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
    int level = 0;
    int nextBallsNum = 1;
    Random random = new Random();
    List<Ball> balls;
    List<Targets> targets;
    private int status = Status_prepare;
    private int statusBeforePause = Status_ready;
    private int panelWidth, panelHeight;

    public Game(int panelWidth, int panelHeight, Vector<Ball> balls, Vector<Targets> targets) {
        this.balls = balls;
        this.targets = targets;
        this.panelHeight = panelHeight;
        this.panelWidth = panelWidth;
    }

    /**
     * 设置panel大小
     */
    public void setPanelSize(int Width, int Height) {
        panelWidth = Width;
        panelHeight = Height;
    }


    public void run() {
        if (status == Status_pause) return;

        else if (status == Status_prepare) {//TODO 旧Targets上移，新Targets准备，游戏结束判断。
            level++;
            for (Targets target : targets) {
                target.getLocation().set(target.getLocation().getX(), target.getLocation().getY() - Constants.distanceLevels);
            }

            for (int i = 0; i < Constants.targetNumPerLevel; i++) {
                Location nextLocation = new Location(
                        Constants.distanceToSide + ((level % 2) / 2 + i) * Constants.distanceBalls,
                        panelHeight - Constants.distanceToBottom);
                int nextHitPoints = random.nextInt(20 + 5 * level) + 1;//TODO 合理的难度设置
                double nextAngle = random.nextDouble();
                switch (random.nextInt() % 3) {//随机选择几何形状
                    case 0:
                        targets.add(new Circle(nextHitPoints, nextLocation, Constants.targetRadius));
                        break;
                    case 1:
                        targets.add(new Rectangle(nextHitPoints, nextLocation, Constants.targetRadius, nextAngle));
                        break;
                    case 2:
                        targets.add(new Triangle(nextHitPoints, nextLocation, Constants.targetRadius, nextAngle));
                }
            }
            status = Status_ready;
            return;
        } else if (status == Status_ready) {//等待鼠标click事件
            return;
        } else { //status==Status_run 元素运动、相交检测
            if (balls.isEmpty()) {
                status = Status_ready;
                return;
            } else
                for (Iterator<Ball> ballIterator = balls.iterator(); ballIterator.hasNext(); ) {
                    Ball next = ballIterator.next();

                }
            for (Iterator<Ball> ballIterator = balls.iterator(); ballIterator.hasNext(); ) {//TODO 使用更高效的算法求交和运动
                Ball ball = ballIterator.next();
                if (ball.isDelay()) ball.delay();
                else {
                    ball.move();
                    if (interactEdge(ball)) ballIterator.remove();
                    for (Iterator<Targets> targetsIterator = targets.iterator(); targetsIterator.hasNext(); ) {//目前是对每个target遍历求交
                        Targets target = targetsIterator.next();
                        if (target.interactBalls(ball)) {
                            ball.setCollided();
                            if (target.hit())
                                targetsIterator.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取下一个弹球发射位置，开始发球
     *
     * @return 如果在正确的状态 (status_ready) 获取了正确范围内的位置，返回真
     */
    public boolean setNextMouseLoc(Location location) {
        Location originLocation = new Location(panelWidth / 2, 0);
        double X = location.getX(), Y = location.getY();
        if ((status == Status_ready) && (X < panelWidth && X > 0 && Y < panelHeight && Y > 0)) {
            status = Status_run;
            /* prepare for balls */
            for (int i = 0; i < nextBallsNum; i++) {
                balls.add(new Ball(Constants.ballRadius, Constants.originalSpeed,
                        -Math.atan2(location.getY() - originLocation.getY(), location.getX() - originLocation.getX()),
                        originLocation, i * Constants.ballDelay));
            }
            return true;
        } else return false;
    }

    /**
     * 将game暂停,保留暂停前状态直到继续
     *
     * @see #goOn()
     * @see #statusBeforePause
     */
    public void pause() {
        if (status != Status_pause) statusBeforePause = status;
        status = Status_pause;
    }

    /**
     * 将暂停的game继续
     */
    public void goOn() {
        status = statusBeforePause;
    }

    /**
     * @return game当前状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 判定与界面边缘相交，并改变运动方向,若离开底部，返回true
     *
     * @param ball 判断小球
     * @return 小球是否离开范围
     */
    boolean interactEdge(Ball ball) {
        double X = ball.getLocation().getX(), Y = ball.getLocation().getY();
        if (X <= 0) {
            ball.getLocation().set(-X, Y);
            ball.reflect(0);
        } else if (X >= panelWidth) {
            ball.getLocation().set(2.0 * panelWidth - X, Y);
            ball.reflect(0);
        }
        if (Y <= 0) {
            ball.getLocation().set(X, -Y);
            ball.reflect(Math.PI / 2.0);
        } else if (Y >= panelHeight) {
            return true;
        }
        return false;
    }

    public void repaintAll(Graphics g) {
        for (Ball ball : balls) {
            ball.paintImage(g);
        }
        for (Targets target : targets) {
            target.paintImage(g);
        }
    }
}
