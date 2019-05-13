package com.example.json.mytouzhisystem.constants;


public enum ConstHttpCode {

    SUCCESS(1, "success"),
    FAIL(-1, "fail"),

    EXCEPTION(-500100, "发生Checked Exception 异常"),
    EXCEPTIONPARAM(-500101, "参数转换错误,参数类型不匹配."),
    EXCEPTIONRANGE(-500102, "参数不在范围之内"),
    RUNTIMEEXCEPTION(-500200, "发生Runtime Exception异常"),
    BUSINESSEXCEPTION(-500201, "发生业务异常"),
    VALIDATE_EXCEPTION(-2, "校验异常"),
    LOGINTYPE_INVALID(-100100, "无效的登录类型"),
    OBJECT_NOT_AVAILABLE(-100101, "对象不可用"),
    CRUDTYPE_INVALID(-100102, "无效的操作类型"),
    DELETE_MEDIA_EXCEPTION(-200101, "删除失败"),
    ADD_MEDIA_EXCEPTION(-200111, "删除失败"),
    PID_EXCEPTION(-1001002, " 未登录,获取PID失败"),
    TAG_ID_EXCEPTION(-1001003, "TAG ID 为空"),
    ALREADY_DELETED_EXCEPTION(-200102, "删除的记录不存在！"),
    NO_VALID_DATA_EXCEPTION(-500100, "未查到有效的数据"),
    EXCEPTION_CLAIM(-500402, "未登录不能投诉"),
    EXCEPTION_INVALID_PID(-500298, "用户信息不存在哦"),
    EXCEPTION_NOT_LOGIN(-500299, "需要登陆哦"),
    EXCEPTION_NOT_LIKE(-500300, "未登录不能点赞"),
    EXCEPTION_NOT_COMMENT(-500301, "未登录不能操作评论"),
    EXCEPTION_NOT_FOLLOW(-500302, "未登录不能查看关注"),
    EXCEPTION_NOT_MEDIA(-500303, "未登录不能操作媒体"),
    MEDIA_NOT_EXISTS(-500304, "media不存在"),
    EXCEPTION_NOT_SHARE(-500305, "未登录不能查看个人分享"),
    EXCEPTION_NOT_CLAIM(-500306, "未登录不能举报"),
    EXCEPTION_NOT_URL(-500307, "未登录获取URL"),
    EXCEPTION_NOT_SAVECLUBGUIDE(-500319, "未登录不能保存俱乐部文章"),
    DELETE_MEDIA_COMMENT_EXCEPTION(-500308, "删除的评论ID与媒体不对应"),
    USER_ID_NULL_EXCEPTION(-500309, "用户ID为空"),
    USER_ID_NOT_EXISTS_EXCEPTION(-500310, "用户不存在"),
    MEDIA_ID_NOT_EXISTS_EXCEPTION(-500311, "删除评论的媒体不存在"),
    USER_ID_INVALID(-500312, "无效的用户ID"),
    MEDIA_ID_INVALID(-500313, "媒体不存在"),
    EXCEPTION_NOT_FAN(-500314, "未登录不能查看粉丝"),
    EXCEPTION_NOT_FAN_OR_FOLLOW(-500315, "未登录不能关注或粉丝"),
    EXCEPTION_ERROR_CLAIM(-500316, "举报失败"),
    USER_ICON_URL_EXCEPTION(-500317, "获取上传头像URL失败"),
    SPECIAL_MUST_BE_FOLLOW_FIRST(-500318, "只能对好友进行特别关注哦"),
    LACK_PERMISSION(-500319, "lack permission"),
    UNABLE_MODIFY_USER_INFO(-500320, "要修改别人的信息，你是Hacker!!!"),
    NOTHING_CHANGED(-500321, "没有什么需要更改..."),

    MNS_SEND_EXCEPTION(-510001, "发送消息失败"),
    MNS_FOR_SEND_EXCEPTION(-510002, "重新发送消息失败"),
    MNS_RECEIVE_EXCEPTION(-510003, "接收消息失败"),

    EXCEPTION_NOT_CREATE_CLUB(-900301, "未登录不能新建俱乐部"),
    EXCEPTION_NOT_UPDATE_CLUB(-900302, "未登录不能修改俱乐部"),
    EXCEPTION_NOT_UPDATE_OTHER_USER_CLUB(-900303, "不能修改他人俱乐部信息"),
    EXCEPTION_NOT_EXIST_CLUB(-900304, "俱乐部不存在"),
    EXCEPTION_CLUB_NOT_NORMAL(-900305, "俱乐部状态为正常才可以推荐为热门"),
    EXCEPTION_NOT_UPDATE_WHEN_CLUB_AUDITING(-900306, "审核中俱乐部不能修改"),


    CLUB_NOT_EXISTS(-900307, "俱乐部不存在"),
    CLUB_MEMBERS_NOT_NOT_EXISTS(-900308, "俱乐部成员已存在"),
    CLUB_MEMBERS_CREATED_CLUB_EXISTS(-900309, "俱乐部创建人不能退出俱乐部"),
    CLUB_MEMBERS_NOT_ROLE_EXISTS(-900310, "没权限做些操作"),
    CLUB_FILEID_NULL_EXISTS(-900311, "图标不能为空哦"),
    CLUB_ICON_NOT_EXISTS(-900312, "俱乐部图标不能为空"),
    CLUB_ICONURL_GET_FAIL(-900313, "俱乐部图标地址获取失败"),
    CLUB_AUDITING_NOT_OVER_FIVE(-900314, "俱乐部未审核最多只能创建5个"),
    CLUB_GUIDE_GET_FAIL(-900315, "俱乐部文章不存在"),
    CLUB_BULLETIN_ID_NULL_EXIST(-900316, "公告不存在"),


    CLUB_SECRET_ERROR(-900109, "密钥校验失败"),
    CLUB_USER_NOT_EXISTS_IN_IM_ERROR(-900110, "用户在IM中不存在"),
    CLUB_DELETE_ONLY_BY_MYSELF(-900111, "只有自己可以解散俱乐部"),
    CLUB_DELETE_ALREADY(-900112, "俱乐部已删除，不能再删除"),
    NOT_EXIST(-900113, "杯具啊！404，可能这个家伙已经飞走了"),
    CLUB_DELETE_AUDTING(-900114, "审核中的俱乐部才可以删除"),


    V5_VALIDATOR_INVALID_SIGNATURE_CODE(-50109, "登录异常"),
    V5_VALIDATOR_INVALID_APPID_CODE(-50110, "登录异常"),
    V5_VALIDATOR_INVALID_NONCE_CODE(-50111, "登录异常"),
    V5_VALIDATOR_TIMEOUT_CODE(-50112, "登录异常"),
    V5_VALIDATE_CODE_INCORRECT_CODE(-40120, "图形验证码不正确"),
    V5_VALIDATE_CODE_INCORRECT_MOBILE_CODE(-40122, "手机验证码不正确"),
    V5_MOBILE_IS_EXIST_CODE(-40121, "该手机号已经注册过"),
    V5_HMAC_CODE_INCORRECT_CODE(-40123, "hmac不正确"),
    V5_VALIDATE_CODE_TIMEOUT_CODE(-40124, "图形验证码过期"),


    YI_LOGIN_ERROR(-59999, "用户名或密码错误"),
    YI_MOBILE_IS_EXIST(-59998, "该手机号已注册过"),
    YI_ACCOUNT_ISNOT_EXIST(-59997, "用户帐号不存在"),
    YI_CHANGE_PSW_ERROR(-59996, "修改密码失败，请重试"),
    YI_REQUEST_RESET_ERROR(-59995, "申请重置密码失败，请重试"),


    YI_PSW_RESET_ERROR(-59994, "重置密码失败，请重试"),
    YI_EMAIL_IS_EXIST(-59993, "该邮箱已注册过"),


    PARAMETER_IS_REQUIRED_CODE(20401, "参数不存在"),

    UNKNOWN(-11111, "unknown");


    // 成员变量
    private int code;      // code
    private String desc;   // name

    ConstHttpCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(int code) {
        for (ConstHttpCode c : ConstHttpCode.values()) {
            if(c.code() == code){
                return c.desc();
            }
        }
        return UNKNOWN.desc;
    }


    public int code(){
        return code;
    }

    public String desc(){
        return desc;
    }



}
