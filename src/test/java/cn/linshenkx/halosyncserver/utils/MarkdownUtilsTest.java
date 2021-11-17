package cn.linshenkx.halosyncserver.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MarkdownUtilsTest {

    private String md = "" +
            "---\n" +
            "title: HBCK2修复RIT实践笔记\n" +
            "id: hbck2_rit\n" +
            "permalink: hbck2_rit/\n" +
            "date: 2020-04-16 01:40:39\n" +
            "categories: \n" +
            "   - [大数据,HBASE]\n" +
            "   - [bug]\n" +
            "top: false\n" +
            "tags:\n" +
            "   - 'hbase'\n" +
            "   - \"debug\"\n" +
            "---\n" +
            "本文记录了作者使用HBCK2工具对线上HBase发生RIT状态的处理，仅供参考，若有疵漏，还望指正。\n" +
            "网络上关于HBCK2的文章很少，基本都是复制粘贴自田竞云(小米)的这一篇：[HBase指南 | HBase 2.0之修复工具HBCK2运维指南](https://mp.weixin.qq.com/s/GVMWwB1WsKcdvZGfvX1lcA?spm=a2c4e.11153940.blogcont683107.11.49d762a815MegW)\n" +
            "事实上这一篇文章介绍得也已经很详细了。这里只是做一些实践上的补充说明。\n" +
            "<!-- more -->\n" +
            "### 1. 下载\n" +
            "直接去[hbase的官网下载地址](https://hbase.apache.org/downloads.html)里就可以找到。这里直接给最新版本的下载链接（截止至2020年4月）：https://downloads.apache.org/hbase/hbase-operator-tools-1.0.0/hbase-operator-tools-1.0.0-bin.tar.gz\n" +
            "但还是推荐自己去git clone编译，因为官网提供的编译版本有滞后性。通常来说，使用最新版本的hbase再搭配使用最新编译的HBCK2,可以解决绝大部分莫名其妙的问题。（fixMeta+restart暴力流）\n" +
            "新版本的HBCK2有更多更方便的功能，不过一般只能在新版本的hbase中使用。";

    @Test
    void replaceTitle() {
        String newTitle = "新标题哦！";
        String newMarkdown = MarkdownUtils.replaceFrontValue(md, "title", newTitle);
        System.out.println(newMarkdown);
        Assertions.assertEquals(newTitle, MarkdownUtils.getTitle(newMarkdown));
    }

    @Test
    void getTitle() {
        Assertions.assertEquals("HBCK2修复RIT实践笔记", MarkdownUtils.getTitle(md));
    }
}