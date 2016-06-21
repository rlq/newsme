package com.he.func.favorite;


import com.he.data.Friend;

import java.util.ArrayList;
import java.util.List;


public class FavoriteModel {

    public List<Friend> friendList;
    public String[] name = {"赵薇","林心如","范冰冰","高圆圆","赵文卓","霍建华","赵丽颖","胡歌","王思聪","黄轩"};
    public String[] url = {
            "http://news.xinhuanet.com/health/2014-10/13/127091729_14131692240341n.jpg",
            "http://fashion.taiwan.cn/news/201202/W020120217287960285002.jpg",
            "https://pic.pimg.tw/alx570601/1434006818-3220185377_n.jpg?v=1434006823",
            "http://r1.ykimg.com/0514000052BBE8D5675839621C02A850",
            // "http://i3.download.fd.pchome.net/g1/M00/02/06/ooYBAFKgQeOIOn7OAAL0GDilF1wAABIsAPcOxUAAvQw743.jpg",
            "http://img.6xw.com/uploads/allimg/120823/6-120R3163021.jpg",
            "http://img.5669.com/uploads/mingxing/huojianhua/14526678690.jpg",
            "http://r3.ykimg.com/0514000055C014ED67BC3C5D2B0A0CC1",
            "http://imge.gmw.cn/attachement/jpg/site2/20160330/f04da226e54a1865913512.jpg",
            "http://pic.wenwo.com/fimg/6094170_365.jpg",
            "http://www.people.com.cn/mediafile/pic/20160308/64/18247735266948800376.jpg"};
    public String[] num = {"2.5","2","3","2.2","1.9","3","2.8","3","2.2","1.5"};
    public String[] baike = {
            "http://baike.baidu.com/item/%E8%B5%B5%E8%96%87/139401",
            "http://baike.baidu.com/view/5765.htm",
            "http://baike.baidu.com/view/9209.htm",
            "http://baike.baidu.com/view/10531.htm",
            "http://baike.baidu.com/item/%E8%B5%B5%E6%96%87%E5%8D%93/139688",
            "http://baike.baidu.com/item/%E9%9C%8D%E5%BB%BA%E5%8D%8E/249243",
            "http://baike.baidu.com/view/568771.htm",
            "http://baike.baidu.com/item/%E8%83%A1%E6%AD%8C/312718",
            "http://baike.baidu.com/subview/3414094/9271922.htm",
            "http://baike.baidu.com/subview/1734686/7769424.htm"};

    public FavoriteModel(){

    }

    public List<Friend> initChatUserData() {
        friendList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Friend friend = new Friend();
            friend.setNickname(name[i]);
            friend.setPortrait(url[i]);
            friend.setChatContent(num[i]);
            friend.setSelected(i%2==0? true:false);
            friendList.add(friend);
        }
        return friendList;
    }

    public String getUrlToBaike(int pos){
        return baike[pos];
    }

}
