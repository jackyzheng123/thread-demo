package com.zjx.other;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * BloomFilter 布隆过滤器
 * 测试布隆过滤器(可用于redis缓存穿透)
 * 一个很长的二进制向量和一系列随机映射函数
 * 布隆过滤器可以用于检索一个元素是否在一个集合中
 *
 * 布隆过滤器的原理是，当一个元素被加入集合时，通过K个散列函数将这个元素映射成一个位数组中的K个点，把它们置为1。
 * 检索时，我们只要看看这些点是不是都是1就（大约）知道集合中有没有它了：如果这些点有任何一个0，则被检元素一定不在；
 * 如果都是1，则被检元素很可能在。这就是布隆过滤器的基本思想。
 *
 * Bloom Filter跟单哈希函数Bit-Map不同之处在于：Bloom Filter使用了k个哈希函数，每个字符串跟k个bit对应。从而降低了冲突的概率。
 *
 * Bloom Filter的缺点
 * bloom filter之所以能做到在时间和空间上的效率比较高，是因为牺牲了判断的准确率、删除的便利性
 * 存在误判:
 *      可能要查到的元素并没有在容器中，但是hash之后得到的k个位置上值都是1。如果bloom filter中存储的是黑名单，那么可以通过建立一个白名单来存储可能会误判的元素。
 * 删除困难:
 *      一个放入容器的元素映射到bit数组的k个位置上是1，删除的时候不能简单的直接置为0，可能会影响其他元素的判断。可以采用Counting Bloom Filter
 *
 *
 *
 * @Description
 * @Author Carson Cheng
 * @Date 2020/9/24 11:55
 * @Version V1.0
 **/
public class TestBloomFilter {

    private static int total = 1000000;
    //private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total);
    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total, 0.0003);

    public static void main(String[] args) {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < total; i++) {
            if (!bf.mightContain(i)) {
                System.out.println("有坏人逃脱了~~~");
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (bf.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);
    }

}