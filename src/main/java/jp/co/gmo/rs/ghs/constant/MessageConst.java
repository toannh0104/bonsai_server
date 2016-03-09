/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.constant;


/**
 * MessageConst
 *
 * @author LongVNH
 * @version 1.0
 */
public class MessageConst {
    /** api upload, upload success */
    public static final String EMSG0001 = "Upload speech success.";

    /** api upload, upload fail */
    public static final String EMSG0002 = "Server has unexpected errors. Please try again.";

    /** api upload, session expired */
    public static final String EMSG0003 = "Session is expired. Please re-login";

    /** showed when lesson name is duplicate */
    public static final String MSG_E0016 = "E0016";

    /** showed when save/ saveAs/ delete failure */
    public static final String MSG_E0017 = "E0017";

    /** api login error */
    public static final String EMSG0004 = "ユーザIDあるいはパスワードは正しくありません。";

    /** api login expired */
    public static final String EMSG0005 = "サーバーエラー。";

    /** api logout error */
    public static final String EMSG0006 = "ログアウト失敗。";

    /**
     * Constructor
     */
    protected MessageConst() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

}