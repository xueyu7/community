package com.xy.community.dto;

import com.xy.community.exception.CustomizeErrorCode;
import com.xy.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    private Object data;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO okOf(List<CommentDTO> commentDTOS) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(commentDTOS);
        return resultDTO;
    }
}
