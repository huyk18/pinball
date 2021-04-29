package gameservice;

import java.awt.*;

public interface Paint {
    /**
     * 对所有元素的绘制
     *
     * @param g 统一的画笔
     */
    void paintImage(Graphics2D g);
}

