package com.xxz.config;

public class ApiUrl {
    /**
     * 测试地址
     */
    //public static String API_DOMAIN = "http://d.api.kuaiyouxi.com";
    /**
     * 正式地址
     */
    public static final String API_DOMAIN = "http://api.xiaolu123.com";

    /**
     * 是否加密开关
     */
    public static final boolean NEED_ENCRYPT = false;

    public static final String SERVER_USER = "/members";//用户中心
    public static final String SERVER_COUNT = "/countapi";//统计
    public static final String SERVER_XIAOLU = "/xiaolu";//小鹿视频

    /**
     * 登陆
     */
    public static String API_LOGIN() {
        return API_DOMAIN + SERVER_USER + "/login.php";

    }

    /**
     * 获取验证码
     */
    public static String API_GETCODE() {
        return API_DOMAIN + SERVER_USER + "/get_phone_code.php";
    }

    /**
     * 注册设置密码
     */
    public static String API_REGISTERED() {
        return API_DOMAIN + SERVER_USER + "/reg.php";
    }

    /**
     * 忘记密码重新设置密码
     */
    public static String API_SETPWD() {
        return API_DOMAIN + SERVER_USER + "/set_pwd.php";
    }


    /**
     * 获取用户信息
     */
    public static String API_GET_USERINFO() {
        return API_DOMAIN + SERVER_USER + "/get_user_info.php";
    }

    /**
     * 用户资料完善
     */
    public static String API_UPDATE_USERINFO() {
        return API_DOMAIN + SERVER_USER + "/update_user_info.php";
    }

    /**
     * 修改密码
     */
    public static String API_UPDATE_PWD() {
        return API_DOMAIN + SERVER_USER + "/reset_pwd.php";

    }

    /**
     * 上传头像接口
     */
    public static String API_HEAD_UPLOAD() {
        return API_DOMAIN + SERVER_USER + "/update_user_picture.php";
    }

    /**
     * 一键注册
     */
    public static String API_ONE_KEY_REGISTER() {
        return API_DOMAIN + SERVER_USER + "/onekey_reg.php";
    }

    /**
     * 个人中心我的视频列表
     *
     * @return
     */
    public static String API_MY_VIDEO_LIST() {
        return createApiUrl(SERVER_USER, "xiaolu_my_video_list.php");
    }

    /**
     * 应用统计接口
     */
    public static String API_APP_COUNT() {
        return API_DOMAIN + SERVER_COUNT + "/video_app_count.php";
    }

    /**
     * 广告统计接口
     */
    public static String API_VERT_COUNT() {
        return API_DOMAIN + SERVER_COUNT + "/vert_count.php";
    }

    /**
     * 频道列表
     * @return
     */
    public static String API_INDEX_NAV() {
        return createApiUrl("video_index_nav.php");
    }

    /**
     * 新增包名上传
     *
     * @return
     */
    public static String API_UPDATE_INDEX_NAV() {
        return createApiUrl("update_index_nav.php");
    }

    /**
     * 删除包名上传
     */
    public static String API_DELETE_INDEX_NAV() {
        return createApiUrl("delete_game_index_nav.php");
    }

    /**
     * 视频点赞/踩
     */
    public static String API_VIDEO_LIKE() {
        return createApiUrl("video_up_down.php");
    }

    /**
     * "全部"页推荐
     *
     * @return
     */
    public static String API_CHANNEL_ALBUM_ALL() {
        return createApiUrl("video_index_game_album.php");
    }

    /**
     * 热门信息（热门标签，热门主播）
     *
     * @return
     */
    public static String API_HOT_INFO() {
        return createApiUrl("video_recom_user_tag_list.php");
    }


    /**
     * 频道专区导航 （游戏专区，分类专区）
     *
     * @return
     */
    public static String API_CHANNEL_TAB_INFO() {
        return createApiUrl("video_game_nav.php");
    }

    /**
     * 专区标签视频列表
     *
     * @return
     */
    public static String API_TAG_VIDEO_LIST() {
        return createApiUrl("video_tag_video_list.php");
    }

    /* 专区主播列表
    * @return
    */
    public static String API_USER_LIST() {
        return createApiUrl("video_user_list.php");
    }

    /**
     * 专区专题列表
     *
     * @return
     */
    public static String API_TOPIC_LIST() {
        return createApiUrl("video_category_list.php");
    }

    /**
     * 专区专题详情
     *
     * @return
     */
    public static String API_TOPIC_DETAIL() {
        return createApiUrl("video_category_video_list.php");
    }


    /**
     * 创建URL
     *
     * @param server
     * @param suffixUrl
     * @return
     */
    private static String createApiUrl(String server, String suffixUrl) {
        return String.format("%s%s/%s", API_DOMAIN, server, suffixUrl);
    }

    /**
     * 创建URL
     *
     * @param suffixUrl
     * @return
     */
    private static String createApiUrl(String suffixUrl) {
        return createApiUrl(SERVER_XIAOLU, suffixUrl);
    }

    /**
     * appid int 视频id packagename string 游戏包名
     */
    public static String API_VIDEO_PLAY() {
        return createApiUrl("video_play.php");
    }

    /**
     * 详情相关
     */
    public static String API_VIDEO_PLAY_ABOUT() {
        return createApiUrl("video_about_video_list.php");
    }


    /**
     * 热门游戏和分类
     */
    public static String API_VIDEO_HOT_GAME_TYPE_LIST() {
        return createApiUrl("video_hot_game_type_list.php");
    }

    /**
     * 热门游戏和分类、PC游戏
     */
    public static String API_VIDEO_HOT_GAME_TYPE_LIST_PC() {
        return createApiUrl("video_hot_game_type_list_pc.php");
    }

    /**
     * 所有游戏
     */
    public static String API_VIDEO_ALL_GAME_LIST() {
        return createApiUrl("video_all_game_list.php");
    }

    /**
     * 小鹿视频获取游戏标签、作者标签、标签视频列表
     */
    public static String API_VIDEO_TAG_COMMON_VIDEO_LIST() {
        return createApiUrl("video_tag_common_video_list.php");
    }

    /**
     * 小鹿视频搜索热词
     */
    public static String API_VIDEO_SEARCH_WORD_RECOMMEND() {
        return createApiUrl("video_search_word_recommend.php");
    }

    /**
     * 小鹿视频搜索
     */
    public static String API_VIDEO_SEARCH() {
        return createApiUrl("video_search.php");
    }

    /**
     * 小鹿视频-获取频道页面－更多频道列表数据
     */
    public static String API_MORE_CHANNEL_LIST() {
        return createApiUrl("video_recom_nav_list.php");
    }

    /**
     * 个人主播详细信息
     *
     * @return
     */
    public static String API_PERSON_DETAIL() {
        return createApiUrl("video_user_info.php");
    }

    /**
     * 个人主播视频列表
     *
     * @return
     */
    public static String API_PERSON_VIDEO_LIST() {
        return createApiUrl("video_user_video_list.php");
    }


    /**
     * 个人主播视频筛选分类
     *
     * @return
     */
    public static String API_PERSON_VIDEO_FILTER() {
        return createApiUrl("video_user_filter_game.php");
    }

    /**
     * 个人主播专辑列表
     *
     * @return
     */
    public static String API_PERSON_TOPIC_LIST() {
        return createApiUrl("video_user_category_list.php");
    }

    /**
     * 获取订阅缺省页数据
     *
     * @return
     */
    public static String API_VIDEO_USER_DEFAULT_SUB_LIST() {
        return createApiUrl("video_user_default_sub_list.php");
    }

    /**
     * 订阅或者取消订阅
     *
     * @return
     */
    public static String API_VIDEO_USER_SUB() {
        return createApiUrl("video_user_sub.php");
    }


    /**
     * 订阅或者取消订阅
     *
     * @return
     */
    public static String API_VIDEO_USER_SUB_LIST() {
        return createApiUrl("video_user_sub_list.php");
    }

    /**
     * 订阅导航列表
     *
     * @return
     */
    public static String API_VIDEO_USER_SUB_INDEX_NAV() {
        return createApiUrl("video_user_sub_index_nav.php");
    }

    /**
     * 订阅视频列表
     *
     * @return
     */
    public static String API_VIDEO_USER_SUB_INDEX() {
        return createApiUrl("video_user_sub_index.php");
    }

    /**
     * 跟多APP列表－广告位接口
     */
    public static String API_Video_VERT() {
        return createApiUrl("video_common_vert.php");
    }

    /**
     * 视频播放超过一页
     *
     * @return
     */
    public static String API_VIDEO_HALF() {
        return createApiUrl("video_watch_exceed_count.php");
    }

    /**
     * 游戏专区推荐
     * @return
     */
    public static String API_ALBUM_RECOMM(){
        return createApiUrl("video_index_game_album_recom.php");
    }

    /**
     * 用户行为统计  type:1：点击游戏 2：点击游戏分类 3：下载视频
     * @return
     */
    public static String API_USER_BEHAVIOR() {
        return createApiUrl("video_user_behavior_count.php");
    }

    /**
     * 猜你喜欢
     *
     * @return
     */
    public static String API_HOME_YOU_LIKE() {
        return createApiUrl("video_index_album.php");
    }


    /**
     * 我的游戏列表
     * @return
     */
    public static String API_MY_GAME_LIST() {
        return createApiUrl("video_user_play_game_list.php");
    }
}
