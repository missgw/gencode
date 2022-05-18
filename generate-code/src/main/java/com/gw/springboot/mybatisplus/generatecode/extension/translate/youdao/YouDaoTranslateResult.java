package com.gw.springboot.mybatisplus.generatecode.extension.translate.youdao;

import java.util.UUID;

public class YouDaoTranslateResult {
    private String tSpeakURL;
    private UUID requestID;
    private String query;
    private String[] translation;
    private String errorCode;
    private Dict dict;
    private Dict webdict;
    private String l;
    private boolean isWord;
    private String speakURL;

    public String getTSpeakURL() {
        return tSpeakURL;
    }

    public void setTSpeakURL(String value) {
        this.tSpeakURL = value;
    }

    public UUID getRequestID() {
        return requestID;
    }

    public void setRequestID(UUID value) {
        this.requestID = value;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String value) {
        this.query = value;
    }

    public String[] getTranslation() {
        return translation;
    }

    public void setTranslation(String[] value) {
        this.translation = value;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    public Dict getDict() {
        return dict;
    }

    public void setDict(Dict value) {
        this.dict = value;
    }

    public Dict getWebdict() {
        return webdict;
    }

    public void setWebdict(Dict value) {
        this.webdict = value;
    }

    public String getL() {
        return l;
    }

    public void setL(String value) {
        this.l = value;
    }

    public boolean getIsWord() {
        return isWord;
    }

    public void setIsWord(boolean value) {
        this.isWord = value;
    }

    public String getSpeakURL() {
        return speakURL;
    }

    public void setSpeakURL(String value) {
        this.speakURL = value;
    }

    public class Dict {
        private String url;

        public String getURL() {
            return url;
        }

        public void setURL(String value) {
            this.url = value;
        }
    }
}


