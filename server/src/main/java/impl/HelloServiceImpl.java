package impl;

import api.entity.HelloObject;
import api.entity.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(HelloObject object) {
        log.info("接受到:{}" , object.getMessage());
        return "这是调用的返回值，id = " + object.getId();
    }
}
