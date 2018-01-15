package music.demo.com;

import java.util.List;

/**
 * Created by liunian on 2018/1/15.
 */

public class LoodBean {

    /**
     * pic : [{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=888&columnid=96","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_15159458467275b5867482d3f7f0e50152414e9d23.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=891&columnid=86","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1515727411737f4576ea2e19d8a15d4e01a34126b6.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=889&columnid=88","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_151572441744add04dfe5a8e85b13a571df135e316.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":2,"mo_type":2,"code":"569191811","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1515997456958a4844f1f4a34520afd060b600963c.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_15159974608655570d27a8c2a6f0e56716bcee6ed6.jpg","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=890&columnid=72","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_15157497870fd3bc699592d1df84856490917ab2f3.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://qr27.cn/BXAQ2V","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_151598347496cccc29778a283efbb5b243acb697c6.jpg","randpic_ios5":"","randpic_desc":"百度音乐xofo联名手气卡","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=863&columnid=91","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1514975087dc8907d25c16ec6f7de84b022c01dfb1.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"}]
     * error_code : 22000
     */

    private int error_code;
    private List<PicBean> pic;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public static class PicBean {
        /**
         * type : 6
         * mo_type : 4
         * code : http://music.baidu.com/h5pc/spec_detail?id=888&columnid=96
         * randpic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_15159458467275b5867482d3f7f0e50152414e9d23.jpg
         * randpic_ios5 :
         * randpic_desc :
         * randpic_ipad :
         * randpic_qq :
         * randpic_2 :
         * randpic_iphone6 :
         * special_type : 0
         * ipad_desc :
         * is_publish : 111111
         */

        private int type;
        private int mo_type;
        private String code;
        private String randpic;
        private String randpic_ios5;
        private String randpic_desc;
        private String randpic_ipad;
        private String randpic_qq;
        private String randpic_2;
        private String randpic_iphone6;
        private int special_type;
        private String ipad_desc;
        private String is_publish;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMo_type() {
            return mo_type;
        }

        public void setMo_type(int mo_type) {
            this.mo_type = mo_type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRandpic() {
            return randpic;
        }

        public void setRandpic(String randpic) {
            this.randpic = randpic;
        }

        public String getRandpic_ios5() {
            return randpic_ios5;
        }

        public void setRandpic_ios5(String randpic_ios5) {
            this.randpic_ios5 = randpic_ios5;
        }

        public String getRandpic_desc() {
            return randpic_desc;
        }

        public void setRandpic_desc(String randpic_desc) {
            this.randpic_desc = randpic_desc;
        }

        public String getRandpic_ipad() {
            return randpic_ipad;
        }

        public void setRandpic_ipad(String randpic_ipad) {
            this.randpic_ipad = randpic_ipad;
        }

        public String getRandpic_qq() {
            return randpic_qq;
        }

        public void setRandpic_qq(String randpic_qq) {
            this.randpic_qq = randpic_qq;
        }

        public String getRandpic_2() {
            return randpic_2;
        }

        public void setRandpic_2(String randpic_2) {
            this.randpic_2 = randpic_2;
        }

        public String getRandpic_iphone6() {
            return randpic_iphone6;
        }

        public void setRandpic_iphone6(String randpic_iphone6) {
            this.randpic_iphone6 = randpic_iphone6;
        }

        public int getSpecial_type() {
            return special_type;
        }

        public void setSpecial_type(int special_type) {
            this.special_type = special_type;
        }

        public String getIpad_desc() {
            return ipad_desc;
        }

        public void setIpad_desc(String ipad_desc) {
            this.ipad_desc = ipad_desc;
        }

        public String getIs_publish() {
            return is_publish;
        }

        public void setIs_publish(String is_publish) {
            this.is_publish = is_publish;
        }
    }
}
