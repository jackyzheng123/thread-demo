package com.zjx.thread.arithmetic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/3/7 9:31
 * @Version V1.0
 **/
public class ArithmeticTest {

    private static AtomicInteger count = new AtomicInteger(0); // 统计查找次数
    private static AtomicInteger binaryCount = new AtomicInteger(0); // 统计查找次数

    public static void main(String[] args) {

        int[] a = {1, 23, 4, 8, 13, 7, 12, 43, 11, 17, 5};
        // 冒泡排序
        System.out.println("排序前：" + showArray(a));
        int[] b = bubbleSort(a);
        System.out.println("排序后：" + showArray(b));

        // 顺序
        System.out.println(sequenceSearch(b, 11));
        // 二分
        int value = binarySearch(b, 11, 0, a.length);
        System.out.println(value);

    }

    /**
     * 顺序查找
     * 数据结构：有序或无序队列
     * 复杂度：O(n)
     * <p>
     * return 查找次数
     */
    public static int sequenceSearch(int[] a, int value) {
        for (int i = 0; i < a.length; i++) {
            count.incrementAndGet();
            if (a[i] == value) {
                return count.intValue();
            }
        }
        return -1;
    }

    /**
     * 二分查找  (必须排好序的)
     * 数据结构：有序数组
     * 复杂度：O(logn)
     *
     * @return 查找次数
     */
    public static int binarySearch(int[] a, int value, int low, int hight) {
        int mid = low + (hight - low) / 2;
        binaryCount.incrementAndGet();
        if (a[mid] == value) {
            return binaryCount.intValue();
        } else if (a[mid] > value) {
            return binarySearch(a, value, low, mid - 1);
        } else {
            return binarySearch(a, value, mid + 1, hight);
        }
    }


    /**
     * 二叉排序树的特点是：
     *
     * 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
     * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
     * 它的左、右子树也分别为二叉排序树。
     *
     * 搜索的原理：
     *
     * 若b是空树，则搜索失败，否则：
     * 若x等于b的根节点的数据域之值，则查找成功；否则：
     * 若x小于b的根节点的数据域之值，则搜索左子树；否则：查找右子树
     * 数据结构：二叉排序树
     * 时间复杂度： O(log2N)
     */

    /**
     * 哈希散列法(哈希表)  HashMap的实现
     *
     * 其原理是首先根据key值和哈希函数创建一个哈希表（散列表），然后根据键值，通过散列函数，定位数据元素位置。
     *
     * 数据结构：哈希表
     * 时间复杂度：几乎是O(1)，取决于产生冲突的多少。
     */


    /**
     * 分块查找:
     * 分块查找又称索引顺序查找，它是顺序查找的一种改进方法。其算法思想是将n个数据元素”按块有序”划分为m块（m ≤ n）。
     * 每一块中的结点不必有序，但块与块之间必须”按块有序”；即第1块中任一元素的关键字都必须小于第2块中任一元素的关键字；
     * 而第2块中任一元素又都必须小于第3块中的任一元素，依次类推。 　　
     *
     * 算法流程：
     * 先选取各块中的最大关键字构成一个索引表；
     * 查找分两个部分：先对索引表进行二分查找或顺序查找，以确定待查记录在哪一块中；然后，在已确定的块中用顺序法进行查找。
     * 这种搜索算法每一次比较都使搜索范围缩小一半。它们的查询速度就有了很大的提升。
     * 如果稍微分析一下会发现，每种查找算法都只能应用于特定的数据结构之上，例如二分查找要求被检索数据有序，而二叉树查找只能应用于二叉查找树上，
     * 但是数据本身的组织结构不可能完全满足各种数据结构（例如，理论上不可能同时将两列都按顺序进行组织），
     * 所以，在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这些数据结构以某种方式引用（指向）数据，这样就可以在这些数据结构上实现高级查找算法。
     * 这种数据结构，就是索引。
     */


    /**
     * 平衡多路搜索树B树（B-tree）
     * B Tree
     *
     * B树（Balance Tree）又叫做B- 树（其实B-是由B-tree翻译过来，所以B-树和B树是一个概念） ，它就是一种平衡路查找树。
     *
     * 有一个根节点，根节点只有一个记录和两个孩子或者根节点为空；
     * 每个节点记录中的key和指针相互间隔，指针指向孩子节点；
     * d是表示树的宽度，除叶子节点之外，其它每个节点有[d/2,d-1]条记录，并且些记录中的key都是从左到右按大小排列的，有[d/2+1,d]个孩子；
     * 在一个节点中，第n个子树中的所有key，小于这个节点中第n个key，大于第n-1个key，比如上图中B节点的第2个子节点E中的所有key都小于B中的第2个key 9，大于第1个key 3;
     * 所有的叶子节点必须在同一层次，也就是它们具有相同的深度；
     *
     */

    /**
     * B+Tree
     *
     * 索引
     */


    /**
     * 冒泡排序
     *
     * @param a
     * @return
     */
    public static int[] bubbleSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
//                    int temp = a[i];
//                    a[i] = a[j];
//                    a[j] = temp;
                    // 改成异或运算交换位置
                    a[i] = a[i] ^ a[j];
                    a[j] = a[i] ^ a[j];
                    a[i] = a[i] ^ a[j];
                }
            }
        }
        return a;
    }

    /**
     * 遍历数组
     */
    public static String showArray(int[] a) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < a.length; i++) {
            if (i == a.length - 1) {
                sb.append(a[i]).append("]");
            } else {
                sb.append(a[i]).append(", ");
            }
        }
        return sb.toString();
    }


    /**
     * 测试^异或运算交换两个数
     */
    @Test
    public void swap() {
//        a=a^b;
//        b=a^b;
//        a=a^b;

        int a = 2;
        int b = 3;
        a = a ^ b;
        System.out.println(a + "," + b);  // 1,3
        b = a ^ b;
        System.out.println(a + "," + b); // 1,2
        a = a ^ b;
        System.out.println(a + "," + b); // 3,2
    }
}
