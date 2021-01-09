package cn.kgc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg {
    private Integer code;
    private String msg;
    private Object data;

    public Msg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Msg data(Object obj){
        return new Msg(200,"成功",obj);
    }

    public static Msg succ(){
        return new Msg(200,"成功");
    }

    public static Msg fail(){
        return new Msg(400,"失败");
    }

    public static Msg hystrix(){
        return new Msg(455,"服务降级");
    }
}
