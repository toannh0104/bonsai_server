/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.response;

import java.util.List;

import jp.co.gmo.rs.ghs.dto.UserLogDto;

/**
 * log reponse dto
 *
 * @author ThuyTTT
 *
 */
public class LogResponseDto extends BaseResponseDto {

    private List<UserLogDto> lisUserLog;
    private Integer totalPage;

    /**
     * @return the lisUserLog
     */
    public List<UserLogDto> getLisUserLog() {
        return lisUserLog;
    }

    /**
     * @param lisUserLog the lisUserLog to set
     */
    public void setLisUserLog(List<UserLogDto> lisUserLog) {
        this.lisUserLog = lisUserLog;
    }

    /**
     * @return the totalPage
     */
    public Integer getTotalPage() {
        return totalPage;
    }

    /**
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
