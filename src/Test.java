

/**
 广度（宽度）优先搜索
 特点： 一层一层的往外扩展
 每次拿出队列首元素进行这一点的扩展
 扩展到的点依次存入队列中
 首元素扩展完了就退出队列（）

 注： 每个点有其 x , y 坐标和其对应走到次点的步数   ，所以用一个节点类来存，方便将整个点入队出队
 走到该点的步数 = 上个点（此时首元素）的步数 + 1；

 */

import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
    }
}

