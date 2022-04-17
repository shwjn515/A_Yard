package com.example.a_yard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBean {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;
    public static List<DataBean> images = new ArrayList();

    public DataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<DataBean> getTestData() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.drawable.icon_phone, "相信自己,你努力的样子真的很美", 1));
        list.add(new DataBean(R.drawable.btu_press, "极致简约,梦幻小屋", 3));
        list.add(new DataBean(R.drawable.icon_passage, "超级卖梦人", 3));
        return list;
    }

    public static List<DataBean> getTestData2() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.drawable.login, "听风.赏雨", 3));
        list.add(new DataBean(R.drawable.p4, "迪丽热巴.迪力木拉提", 1));
        return list;
    }

    /**
     * 仿淘宝商品详情第一个是视频
     *
     * @return
     */
    public static List<DataBean> getTestDataVideo() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4", "第一个放视频", 2));
        list.add(new DataBean(R.drawable.bj_denglu, "听风.赏雨", 1));
        return list;
    }

    public static List<DataBean> getTestData3() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("https://www.zhongguofeng.com/uploads/allimg/170511/5-1F511162339.png", null, 1));
        list.add(new DataBean("https://www.zhongguofeng.com/uploads/allimg/170511/5-1F511162337.png", null, 1));
        list.add(new DataBean("https://www.zhongguofeng.com/uploads/allimg/180726/13-1PH61U402.jpg", null, 1));
        list.add(new DataBean("https://www.zhongguofeng.com/uploads/allimg/170405/5-1F4051FJ7-50.jpg", null, 1));
        list.add(new DataBean("https://www.zhongguofeng.com/uploads/allimg/170405/5-1F4051FJ7.jpg", null, 1));
        return list;
    }

    public static List<DataBean> getVideos() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4", null, 0));
        return list;
    }


    public static List<String> getColors(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandColor());
        }
        return list;
    }
    public static boolean addImages(String imageUrl){
        images.add(new DataBean(imageUrl, null, 0));
        return true;
    }

    public static List<DataBean> getImages(){
        return images;
    }
     /**
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return String
     */
    public static String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }
}