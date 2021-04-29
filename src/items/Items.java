package items;

import gameservice.Paint;

/**
 * 游戏内所有主要元素的基类
 */
abstract public class Items implements Paint {
    float radius;//几何体最小外接圆半径
    private Location location = new Location(0, 0);

    public Location getLocation() {
        return location;
    }

}