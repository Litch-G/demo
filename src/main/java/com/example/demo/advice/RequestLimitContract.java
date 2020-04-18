package com.example.demo.advice;

import com.example.demo.exception.CustomizeException;
import com.example.demo.exception.RequestLimitException;
import com.example.demo.time.RequestLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Aspect
@Component
public class RequestLimitContract {
    private Map<String, Integer> redisTemplate=new HashMap<String,Integer>();
    @Before("within(@org.springframework.stereotype.Controller *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {
        try {
            //获取 HttpServletRequest 参数
            Object[] args = joinPoint.getArgs();
            HttpServletRequest request = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    request = (HttpServletRequest) args[i];
                    break;
                }
            }
            if (request == null) {
                throw new CustomizeException("方法中缺失HttpServletRequest参数");
            }
            String ip = request.getLocalAddr();
            String url = request.getRequestURL().toString();
            final String key = "req_limit_".concat(url).concat(ip);
            System.out.println("ip = "+ip+"\n"+" url = "+url+"\n"+" key = "+key);
            if(redisTemplate.get(key)==null || redisTemplate.get(key)==0){
                redisTemplate.put(key,1);
            }else{
                redisTemplate.put(key,redisTemplate.get(key)+1);
            }
            int count = redisTemplate.get(key);
            if (count > 0) {
                Timer timer= new Timer();
                TimerTask task  = new TimerTask(){    //创建一个新的计时器任务。
                    @Override
                    public void run() {
                        if(!key.equals("")) {
                            redisTemplate.remove(key);
                        }
                    }
                };
                timer.schedule(task, limit.time());
                //安排在指定延迟后执行指定的任务。task : 所要安排的任务。limit.time() : 执行任务前的延迟时间，单位是毫秒。
            }
            if (count > limit.count()) {
                throw new RequestLimitException("请不要快速刷新访问网页");
            }
        } catch (RequestLimitException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
