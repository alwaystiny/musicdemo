package music.demo.com.bean;

import java.util.List;

/**
 * Created by liunian on 2018/1/15.
 */

public class RecommendListRecommendInfo {
    /**
     * position : 1
     * tag : 摇滚,怀旧,经典
     * songidlist : []
     * pic : http://musicugc.cdn.qianqian.com/ugcdiy/pic/2380b98a77d86a8c74168cc3c144a15d.jpg
     * title : 台湾摇滚教父伍佰黄金精选
     * collectnum : 263
     * type : gedan
     * listenum : 75007
     * listid : 509777622
     */

    private int position;
    private String tag;
    private String pic;
    private String title;
    private int collectnum;
    private String type;
    private int listenum;
    private String listid;
    private List<?> songidlist;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(int collectnum) {
        this.collectnum = collectnum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getListenum() {
        return listenum;
    }

    public void setListenum(int listenum) {
        this.listenum = listenum;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public List<?> getSongidlist() {
        return songidlist;
    }

    public void setSongidlist(List<?> songidlist) {
        this.songidlist = songidlist;
    }
}
