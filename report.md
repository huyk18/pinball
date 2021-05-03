## 测试

通过调整 `Constants.fps` 参数为6000模拟运算速度极慢机器上的表现，出现如下报错：

```
Exception in thread "AWT-EventQueue-0" java.util.ConcurrentModificationException
	at java.base/java.util.LinkedList$ListItr.checkForComodification(LinkedList.java:970)
	at java.base/java.util.LinkedList$ListItr.next(LinkedList.java:892)
	at ui.GamePanel.drawBufferedImage(GamePanel.java:58)
	at ui.GamePanel.paint(GamePanel.java:45)
```

`GamePanel`类与`Game`类的线程同时遍历物体数组发生冲突。解决方案：对可能发生并发冲突的线程加锁。

### 序列化

`writeObject`在重复写入具有同一序列号的对象时不会更新对象，因此做不到每变化一次就写入一次，干脆自己实现。