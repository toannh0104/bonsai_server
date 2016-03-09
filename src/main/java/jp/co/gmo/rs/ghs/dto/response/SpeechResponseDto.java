/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.response;

import jp.co.gmo.rs.ghs.dto.ResultDto;

/**
 * SpeechResponseDto
 *
 * @author LongVNH
 *
 */
public class SpeechResponseDto extends BaseResponseDto {

    private ResultDto resultDto;

    /**
     * @return the resultDto
     */
    public ResultDto getResultDto() {
        return resultDto;
    }

    /**
     * @param resultDto the resultDto to set
     */
    public void setResultDto(ResultDto resultDto) {
        this.resultDto = resultDto;
    }

}
