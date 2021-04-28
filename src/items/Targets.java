package items;

abstract public class Targets extends Items {
    int hitPoints;

    Targets(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * ball撞击target
     *
     * @return 若target被撞消失，返回true
     */
    public boolean hit() {
        hitPoints--;
        if (hitPoints == 0) return true;
        else return false;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * 如果与ball相交，改变其运动角度，返回true
     *
     * @param ball 相交对象
     * @return 是否与ball相交
     */
    abstract public boolean interactBalls(Ball ball);

    public double distanceWithBall(Ball ball) {
        return Math.sqrt(Math.abs(Math.pow(this.getLocation().getX() - ball.getLocation().getX(), 2) + Math.pow(this.getLocation().getY() - ball.getLocation().getY(), 2)));
    }
}
