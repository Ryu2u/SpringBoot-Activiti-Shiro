package com.ryuzu.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ryuzu
 * @date 2022/7/20 15:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMsg implements Serializable {
    private static final long serialVersionUID = 8161498954844648408L;
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回携带的数据
     */
    private Object obj;

    public ResultMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回成功结果
     * @param code
     * @param msg
     * @return
     */
    public static ResultMsg success(Integer code, String msg) {
        return new ResultMsg(code, msg);
    }

    /**
     * 返回默认的成功结果
     * @return
     */
    public static ResultMsg ok(){
        return ResultMsg.success(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg());
    }

    /**
     * 返回成功结果
     * 携带数据
     * @param code
     * @param msg
     * @param obj
     * @return
     */
    public static ResultMsg success(Integer code, String msg,Object obj) {
        return new ResultMsg(code, msg,obj);
    }

    /**
     * 返回失败结果
     * @param code
     * @param msg
     * @return
     */
    public static ResultMsg error(Integer code, String msg) {
        return new ResultMsg(code, msg);
    }

    /**
     * 返回失败结果
     * 携带数据
     * @param code
     * @param msg
     * @param obj
     * @return
     */
    public static ResultMsg error(Integer code, String msg,Object obj) {
        return new ResultMsg(code, msg,obj);
    }


}
