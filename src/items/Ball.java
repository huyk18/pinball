package items;

import gameservice.Constants;

import java.awt.*;

public class Ball extends Items {
    /**
     * angle 与x轴正半轴所成角度 [0,2*PI) 弧度制
     * 注意：由于窗体左上角为零点，角度为逆时针
     */
    private double directionAngle;
    private double speed;//pixel per frame
    private int delay;
    private double cosDirectionAngle, sinDirectionAngle;
    private boolean isCollided = false;//是否经过碰撞，用于启用重力加速度

    /**
     * 初始化弹球
     *
     * @param radius         弹球半径
     * @param originalSpeed  弹球初始速度
     * @param directionAngle 弹球运动方向
     * @param location       弹球初始位置
     * @param ballDelay      弹球延迟发射帧数
     */
    public Ball(double radius, double originalSpeed, double directionAngle, Location location, int ballDelay) {
        this.radius = radius;
        speed = originalSpeed;
        setDirectionAngle(directionAngle);
        this.getLocation().set(location);
        delay = ballDelay;
    }

    public boolean isDelay() {
        return (delay > 0);
    }

    public void delay() {
        delay--;
    }

    public boolean isInteract(Items anotherItem) {
        return true;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirectionAngle() {
        return directionAngle;
    }

    /**
     * 设置方向角，并化为 [0,2pi)
     *
     * @param directionAngle 运动方向角
     */
    public void setDirectionAngle(double directionAngle) {
        this.directionAngle = directionAngle;
        directionAngle = directionAngle % (Math.PI * 2.0);
        /* to accelerate the process */
        cosDirectionAngle = Math.cos(directionAngle);
        sinDirectionAngle = Math.sin(directionAngle);
    }

    /**
     * 沿方向角移动speed个像素,对速度和方向施加重力影响
     * TODO 在第一次碰撞前不受重力影响，
     */
    public void move() {
        double speedX = speed * cosDirectionAngle;
        double speedY = speed * sinDirectionAngle;
        if (isCollided()) {
            speedY += Constants.gravAcc;
            speed = Math.sqrt(speedX * speedX + speedY * speedY);
            setDirectionAngle(Math.atan2(speedY, speedX));
        }
        double X = this.getLocation().getX();
        double Y = this.getLocation().getY();
        X += speedX;
        Y += speedY;
        this.getLocation().set(X, Y);
    }

    /**
     * 小球反弹：改变小球方向，补偿小球过相交位置
     * TODO 非弹性碰撞算法
     *
     * @param normalDirection 法线方向，弧度制
     * @param collideLocation 理论相撞时小球位置
     */
    public void reflect(double normalDirection,Location collideLocation) {
        double _directionAngle = (2.0 * normalDirection + Math.PI - directionAngle) % (2 * Math.PI);
        setDirectionAngle(_directionAngle);
    }

    @Override
    public void paintImage(Graphics g) {
        //TODO
        g.drawOval((int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius), (int) (2.0 * radius), (int) (2.0 * radius));
    }

    public boolean isCollided() {
        return isCollided;
    }

    public void setCollided() {
        isCollided = true;
    }
}
