/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.constant;


/**
 * ConstValues
 *
 * @author ThuyTTT
 *
 */
public final class ConstValues {
    private ConstValues() {
    }

    public static final int DELETE_FLAG_VALUES_OFF = 0;
    public static final int DELETE_FLAG_VALUES_ON = 1;
    public static final int TYPE_INSERT = 1;
    public static final int TYPE_UPDATE = 2;
    public static final int TYPE_DELETE = 3;
    public static final String GOOGLE_TRANSLATE_TEXT = "http://translate.google.com.br/translate_a/single?";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MODE_LEARNING_SCENARIO_JP = "構文";
    public static final String MODE_LEARNING_VOCABULARY_JP = "単語";
    public static final String MODE_PRACTICE_JP = "練習";
    public static final String MODE_TEST_JP = "テスト";
    public static final int NEXT_ELEMENT = 4;
    public static final String DEFAULT_LANGUAGE = "EN";
    public static final String IS_NEW = "isNew";
    public static final Integer MODE_SCENARIO = 0;
    public static final Integer MODE_VOCABULARY = 1;
    public static final String MODE_JAPANESE_STR = "lesson";
    public static final String MODE_SECURITY_STR = "security";
    public static final Integer MODE_JAPANESE = 1;
    public static final Integer MODE_SECURITY = 2;
    public static final Integer MODE_LOG_LOGIN = 0;
    public static final Integer MODE_LOG_DATA = 1;
    public static final int FIRST_PAGE = 1;
    public static final int TOTAL_ITEM_IN_TABLE = 5;

    /** Sample rate wav file, default 16000HZ */
    public static final int SAMPLE_RATE_WAV = 16000;

    public static final int JEDIS_EXPIRE_TIME = 864000;

    /** Client practice/test constant */
    public static final int FULL_PERCENT = 100;
    public static final int LESSONINFO_COMPLETE_COUNT = 6;
    public static final String PRACTICE = "practice";
    public static final String TEST = "test";
    public static final String MEMORY_TEXT = "memory";
    public static final String WRITING_TEXT = "writing";
    public static final String CONVERSATION_TEXT = "speech";
    public static final String MEMORY_TEXT_JP = "記憶力";
    public static final String WRITING_TEXT_JP = "筆記力";
    public static final String CONVERSATION_TEXT_JP = "会話力";
    public static final int MODE_MEMORY = 1;
    public static final int MODE_WRITING = 2;
    public static final int MODE_CONVERSATION = 3;
    public static final String WORDTYPE_PRACTICLE = "Particle";
    public static final String WORDTYPE_VERB = "Verb";
    public static final int MODE_LANGUAGE_ROMAJI = 0;
    public static final int MODE_LANGUAGE_KANA = 1;
    public static final int MODE_LANGUAGE_KANJI = 2;
    public static final String GO_NEXT = "next";
    public static final String MEMORY_SCORE_TEXT = "_memory_score";
    public static final String WRITING_SCORE_TEXT = "_writing_score";
    public static final String CONVERSATION_SCORE_TEXT = "_conversation_score";
    public static final String PRACTICE_MEMORY_SCORE = "practice_memory_score";
    public static final String PRACTICE_WRITING_SCORE = "practice_writing_score";
    public static final String PRACTICE_CONVERSATION_SCORE = "practice_conversation_score";
    public static final String TEST_MEMORY_SCORE = "test_memory_score";
    public static final String TEST_WRITING_SCORE = "test_writing_score";
    public static final String TEST_CONVERSATION_SCORE = "test_conversation_score";
    public static final String DICT_TEXT = "Dict";
    /** Init Default kana array size 20 */
    public static final int DEFAULT_KANA_ARRAY_SIZE = 20;

    /**
     * Response status
     */
    public final class Status {
        private Status() {
        }

        public static final String OK = "OK";
        public static final String NG = "NG";
    }

    /**
     * Constant for page
     */
    public final class PageDefault {
        private PageDefault() {
        }

        public static final int PAGE_START = 1;
        public static final int SIZE_PER_PAGE = 5;
    }

    public static final String MEMCACHED_NAME = "Lesson";
    public static final String REDIS_HOST = "redis.host";
    public static final String REDIS_PORT = "redis.port";

    /**
     * Constant for thread
     */
    public final class ThreadControls {
        private ThreadControls() {
        }

        public static final int SLEEP = 1000;
        public static final int COUNT = 5;
    }

    /**
     * Constant for length kana kanji romaji
     */
    public final class KanaKanjiRomaji {
        private KanaKanjiRomaji() {
        }

        public static final int KANA_LIMIT_LENGTH = 50;
        public static final int LANG_LIMIT_LENGTH = 255;
    }

    /**
     * Constant Vocabulary lesson
     */
    public final class VocabularySpeech {
        private VocabularySpeech() {
        }

        public static final String DEFAULT_LESSON_NO = "1";
        public static final String DEFAULT_INDEX = "0";
        public static final String DEFAULT_GRAMMA_RULE = "Vocabulary";
        public static final String DEFAULT_COMPONENT_NAME = "Vocabulary";
        // Sign: The verb sign. If -1, use all signs.
        public static final String ALL_VERB_SIGNS = "-1";
        // Tense: The verb tense. If -1, use all tenses.
        public static final String ALL_VERB_TENSES = "-1";
        // Style: The verb politeness. If -1, use all styles.
        public static final String ALL_VERB_STYLES = "-1";
        // Question: Whether a question or not. If -1, use both options
        public static final String ALL_QUESTION_OPTIONS = "-1";
    }

    public static final int IS_NULL = -1;
    public static final String TEST_AREA_SCORE_SCENARIO = "scenario";
    public static final int USER_INPUT_FLAG = 0;
    public static final int LEXICON_USER_INPUT_LEVEL = 1;
    public static final int LEXICON_USER_INPUT_TYPE = 1;
    public static final String LEXICON_USER_INPUT_PICTURE = "<none>";
    public static final String LEXICON_USER_INPUT_CHINESE = "";
    public static final int LEXICON_SAME_LIST = 1;
    public static final int LEXICON_DIFFERENT_LIST = 0;
    public static final Integer ROLE_ADMIN = 1;
    public static final Integer ROLE_USER = 2;
    public static final String CONST_STRING_EMPTY = "";

    /**
     * Constant for TokenAuthentication
     */
    public final class TokenAuthentication {
        private TokenAuthentication() {
        }

        /* Token exipre time, second per unit */
        public static final int TOKEN_EXPIRE_TIME  = 3600;

        /* Token name */
        public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

        /* Claims */
        public static final String CLAIM_USER_ID  = "userId";
        public static final String CLAIM_USER_NAME  = "userName";
        public static final String CLAIM_USER_LANG  = "nativeLanguage";
        public static final String CLAIM_UUID  = "uuid";
        public static final String CLAIM_ID_LOG = "idLog";
        public static final String CLAIM_DEVICE_NAME = "deviceName";
        public static final String CLAIM_DEVICE_VERSION = "deviceVersion";
        public static final String CLAIM_LOCATION ="location";

        /* API */
        public static final String API_SPEECH_UPLOAD = "/speech/uploadFileBlob";
        public static final String API_ERROR = "/error";

        /* Xml request */
        public static final String X_REQUEST_HEADER_NAME = "X-Requested-With";
        public static final String XML_REQUEST = "XMLHttpRequest";
    }
}
