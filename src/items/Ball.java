package items;

import gameservice.Constants;

import java.awt.*;

/**
 * 小球类
 */
public class Ball extends Items {
    /**
     * angle 与x轴正半轴所成角度 [0,2*PI) 弧度制
     * 注意：由于窗体左上角为零点，角度为逆时针
     */
    private float directionAngle;
    private float speed;//pixel per frame
    private int delay;//发射延迟帧
    private float cosDirectionAngle, sinDirectionAngle;
    private boolean isCollided = false;//是否经过碰撞，用于启用重力加速度
    private boolean enhanced=false;
    /**
     * 初始化弹球
     *
     * @param radius         弹球半径
     * @param originalSpeed  弹球初始速度
     * @param directionAngle 弹球运动方向
     * @param location       弹球初始位置
     * @param ballDelay      弹球延迟发射帧数
     */
    public Ball(float radius, float originalSpeed, float directionAngle, Location location, int ballDelay,boolean enhanced) {
        this.radius = radius;
        speed = originalSpeed;
        setDirectionAngle(directionAngle);
        this.getLocation().set(location);
        delay = ballDelay;
        this.enhanced=enhanced;
    }

    public boolean isDelay() {
        return (delay > 0);
    }

    public void delay() {
        delay--;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDirectionAngle() {
        return directionAngle;
    }

    /**
     * 设置方向角，并化为 [0,2pi)
     *
     * @param directionAngle 运动方向角
     */
    public void setDirectionAngle(float directionAngle) {
        this.directionAngle = directionAngle;
        directionAngle = directionAngle % (Constants.PI * 2.0f);
        /* to accelerate the process */
        cosDirectionAngle = (float) Math.cos(directionAngle);
        sinDirectionAngle = (float) Math.sin(directionAngle);
    }

    /**
     * 沿方向角移动speed个像素,对速度和方向施加重力影响
     * 在第一次碰撞前不受重力影响，
     */
    public void move() {
        float speedX = speed * cosDirectionAngle;
        float speedY = speed * sinDirectionAngle;
        if (isCollided()) {
            speedY += Constants.gravAcc;
            speed = (float) Math.sqrt(speedX * speedX + speedY * speedY);
            setDirectionAngle((float) Math.atan2(speedY, speedX));
        }
        float X = this.getLocation().getX();
        float Y = this.getLocation().getY();
        X += speedX;
        Y += speedY;
        this.getLocation().set(X, Y);
    }

    /**
     * 小球反弹：改变小球方向，补偿小球过相交位置
     * 非弹性碰撞算法，仅垂直速度同时衰减并反向
     *
     * @param normalDirection 法线方向，弧度制
     * @param collideLocation 理论相撞时小球位置，用于位置补偿
     */
    public void reflect(float normalDirection, Location collideLocation) {
        setCollided();
        float speedVertical = -getSpeed() * ((float) Math.cos(getDirectionAngle() - normalDirection)) * Constants.speedLoss;
        float speedHorizontal = getSpeed() * ((float) Math.sin(getDirectionAngle() - normalDirection));
        setSpeed((float) Math.sqrt(speedHorizontal * speedHorizontal + speedVertical * speedVertical));
        setDirectionAngle(normalDirection + (float) Math.atan2(speedHorizontal, speedVertical));
        float cX = collideLocation.getX(), cY = collideLocation.getY();
        float distance = this.getLocation().distance(collideLocation);
        this.getLocation().set(cX + distance * cosDirectionAngle, cY + distance * sinDirectionAngle);
    }

    /**
     * 小球反弹：改变小球方向，不补偿位置
     * 非弹性碰撞算法
     *
     * @param normalDirection 法线方向，弧度制
     */
    public void reflect(float normalDirection) {
        setCollided();
        float speedVertical = -getSpeed() * ((float) Math.cos(getDirectionAngle() - normalDirection)) * Constants.speedLoss;
        float speedHorizontal = getSpeed() * ((float) Math.sin(getDirectionAngle() - normalDirection));
        setSpeed((float) Math.sqrt(speedHorizontal * speedHorizontal + speedVertical * speedVertical));
        setDirectionAngle(normalDirection + (float) Math.atan2(speedHorizontal, speedVertical));
    }

    @Override
    public void paintImage(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval((int) (getLocation().getX() - radius - Constants.paintBallRadiusBias), (int) (getLocation().getY() - radius - Constants.paintBallRadiusBias),
                (int) (2.0 * (radius + Constants.paintBallRadiusBias)), (int) (2.0 * (radius + Constants.paintBallRadiusBias)));

    }

    public boolean isCollided() {
        return isCollided;
    }

    public void setCollided() {
        isCollided = true;
    }

    public boolean isEnhanced() {
        return enhanced;
    }
}
