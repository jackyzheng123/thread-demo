package com.zjx.arithmetic;

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

        int[] a = {55, 34, 1, 23, 4, 8, 13, 7, 12, 43, 11, 17, 5};
        // 冒泡排序
        System.out.println("排序前：" + showArray(a));
        // int[] b = bubbleSort(a);
        int[] b = selectSort(a);
        System.out.println("排序后：" + showArray(b));

        // 顺序查找
        System.out.println("顺序查找次数：" + sequenceSearch(b, 11));
        // 二分法查找
        int value = binarySearch(b, 11, 0, a.length);
        System.out.println("二分法查找次数：" + value);

        // 二叉树排序
        BinaryTree bt = new BinaryTree();
        bt.add(3);
        bt.add(5);
        bt.add(4);
        bt.add(8);
        bt.add(7);
        bt.add(8);
        bt.add(1);
        bt.print();
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
     * 二叉树排序的特点是：
     * <p>
     * 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
     * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
     * 它的左、右子树也分别为二叉排序树。
     * <p>
     * 搜索的原理：
     * <p>
     * 若b是空树，则搜索失败，否则：
     * 若x等于b的根节点的数据域之值，则查找成功；否则：
     * 若x小于b的根节点的数据域之值，则搜索左子树；否则：查找右子树
     * 数据结构：二叉排序树
     * 时间复杂度： O(log2N)
     */
    static class BinaryTree {
        class Node {          //声明一个节点类
            private Comparable data;  //节点的数据类型为Comparable
            private Node left;   //保存左子树
            private Node right;  //保存右子树

            public Node(Comparable data) {   //构造函数
                this.data = data;
            }

            public void addNode(Node newNode) {
                //确定是放在左子树还是右子树
                if (newNode.data.compareTo(this.data) < 0) {  //新节点值小于当前节点
                    if (this.left == null) {
                        this.left = newNode;  //左子树为空的话，新节点设为左子树
                    } else {
                        this.left.addNode(newNode); //否则继续向下判断
                    }
                } else {  //新节点的值大于或等于当前节点
                    if (this.right == null) {
                        this.right = newNode;
                    } else {
                        this.right.addNode(newNode);
                    }
                }
            }

            public void printNode() {  //采用中序遍历
                if (this.left != null) {   //如果不为空先输出左子树
                    this.left.printNode();
                }
                System.out.print(this.data + "\t");  //输出当前根节点
                if (this.right != null) {  //输出右子树
                    this.right.printNode();
                }
            }
        }

        private Node root;    //表示根元素

        public void add(Comparable data) {    //向二叉树中插入元素
            Node newNode = new Node(data);
            if (root == null) {   //没有根节点
                root = newNode;
            } else {
                root.addNode(newNode); //判断放在左子树还是右子树
            }
        }

        public void print() {
            root.printNode();   //根据根节点输出
        }
    }


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
     *
     * B-Tree算法：
     *      首先从根节点进行二分查找，如果找到则返回对应节点的data，否则对相应区间的指针指向的节点递归进行查找，
     *      直到找到节点或找到null指针，前者查找成功，后者查找失败。B-Tree上查找算法的伪代码如下：
     *
     *      BTree_Search(node, key) {
     *          if(node == null){
     *              return null;
     *          }
     *
     *          foreach(node.key){
     *              if(node.key[i] == key){
     *                  return node.data[i];
     *              }
     *              if(node.key[i] > key){
     *                  return BTree_Search(point[i] -> node);
     *              }
     *          }
     *          retrun BTree_Search(point[i+1] -> node);
     *      }
     *      data = BTree_Search(root, my_key);
     *
     */


    /**
     * B+Tree
     * 其实B-Tree有许多变种，其中最常见的是B+Tree，比如MySQL就普遍使用B+Tree实现其索引结构。B-Tree相比，B+Tree有以下不同点：
     *
     * 每个节点的指针上限为2d而不是2d+1；
     * 内节点不存储data，只存储key；
     * 叶子节点不存储指针；
     *
     * 带有顺序访问指针的B+Tree:
     *          一般在数据库系统或文件系统中使用的B+Tree结构都在经典B+Tree的基础上进行了优化，增加了顺序访问指针。
     *
     * MyISAM索引实现(非聚集索引)
     *  MyISAM引擎使用B+Tree作为索引结构，叶节点的data域存放的是数据记录的地址
     *  MyISAM索引文件和数据文件是分离的，索引文件仅保存数据记录的地址
     *
     *
     *  InnoDB索引实现(聚集索引)
     *      InnoDB的数据文件本身就是索引文件,表数据文件本身就是按B+Tree组织的一个索引结构，这棵树的叶节点data域保存了完整的数据记录
     *      InnoDB表数据文件本身就是主索引
     *      InnoDB的辅助索引data域存储相应记录主键的值而不是地址
     *      聚集索引这种实现方式使得按主键的搜索十分高效，但是辅助索引搜索需要检索两遍索引：首先检索辅助索引获得主键，然后用主键到主索引中检索获得记录。
     *
     *     主键选择：
     *      为什么不建议使用过长的字段作为主键，因为所有辅助索引都引用主索引，过长的主索引会令辅助索引变得过大。
     *      用非单调的字段作为主键在InnoDB中不是个好主意，因为InnoDB数据文件本身是一颗B+Tree，非单调的主键会造成在插入新记录时数据文件为了维持B+Tree的特性而频繁的分裂调整，十分低效，而使用自增字段作为主键则是一个很好的选择。
     *
     *  索引的使用和优化：
     *      联合索引（复合索引） -- 最左前缀原理
     *      前缀索引
     *          字符串列需要进行全字段匹配或者前匹配。也就是=‘xxx’ 或者 like ‘xxx%’
     *          MySQL 不能在 ORDER BY 或 GROUP BY 中使用前缀索引，也不能把它们用作覆盖索引
     *
     * 索引优化策略:
     *      1. 最左前缀匹配原则
     *      2. 主键外检一定要建索引
     *      3. 对 where,on,group by,order by 中出现的列使用索引
     *      4. 尽量选择区分度高的列作为索引,区分度的公式是count(distinct col)/count(*)，表示字段不重复的比例，比例越大我们扫描的记录数越少，唯一键的区分度是1，而一些状态、性别字段可能在大数据面前区分度就是0
     *      5. 对较小的数据列使用索引,这样会使索引文件更小,同时内存中也可以装载更多的索引键
     *      6. 索引列不能参与计算，保持列“干净”，比如fromunixtime(createtime) = ’2014-05-29’就不能使用到索引，原因很简单，b+树中存的都是数据表中的字段值，但进行检索时，需要把所有元素都应用函数才能比较，显然成本太大。所以语句应该写成createtime = unixtimestamp(’2014-05-29’);
     *      7. 为较长的字符串使用前缀索引
     *      8. 尽量的扩展索引，不要新建索引。比如表中已经有a的索引，现在要加(a,b)的索引，那么只需要修改原来的索引即可
     *      9. 不要过多创建索引, 权衡索引个数与DML之间关系，DML也就是插入、删除数据操作。这里需要权衡一个问题，建立索引的目的是为了提高查询效率的，但建立的索引过多，会影响插入、删除数据的速度，因为我们修改的表数据，索引也需要进行调整重建
     *      10. 对于like查询，”%”不要放在前面。
     *      11. 查询where条件数据类型不匹配也无法使用索引
     *      12. 正则表达式不使用索引
     *
     *
     */


    /**
     * 冒泡排序
     * <p>
     * 第一个元素与其他剩余元素做比较，如果第一个元素大于其他元素，则交换两者位置，... 如此类推，第二个，第三个
     * <p>
     * 时间复杂度：O(n) - O(n^2)
     *
     * @param arr
     * @return
     */
    public static int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) { //外层循环控制排序趟数
            for (int j = i + 1; j < arr.length; j++) { //内层循环控制每一趟排序多少次
                if (arr[i] > arr[j]) {
//                    int temp = arr[i];
//                    arr[i] = arr[j];
//                    arr[j] = temp;
                    // 改成异或运算交换位置
                    arr[i] = arr[i] ^ arr[j];
                    arr[j] = arr[i] ^ arr[j];
                    arr[i] = arr[i] ^ arr[j];
                }
            }
        }
        return arr;
    }

    /**
     * 选择排序
     * <p>
     * 每次选出最小的放前面
     * 时间复杂度：
     */
    public static int[] selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {// 做第i趟排序
            int k = i;
            for (int j = k + 1; j < arr.length; j++) {// 选最小的记录
                if (arr[j] < arr[k]) {
                    k = j; //记下目前找到的最小值所在的位置
                }
            }
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if (i != k) {  //交换a[i]和a[k]
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }
        return arr;
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
