package org.example.cp.oms.domain.service;

import org.example.cp.oms.domain.ability.PostPersistAbility;
import org.example.cp.oms.domain.ability.SerializableIsolationAbility;
import org.example.cp.oms.domain.step.SubmitOrderStepsExec;
import io.github.dddplus.annotation.DomainService;
import io.github.dddplus.model.IDomainService;
import io.github.dddplus.runtime.DDD;
import lombok.extern.slf4j.Slf4j;
import org.example.cp.oms.domain.CoreDomain;
import org.example.cp.oms.domain.ability.DecideStepsAbility;
import org.example.cp.oms.spec.exception.OrderErrorReason;
import org.example.cp.oms.spec.exception.OrderException;
import org.example.cp.oms.domain.model.OrderMain;
import org.example.cp.oms.spec.Steps;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.locks.Lock;

@DomainService(domain = CoreDomain.CODE)
@Slf4j
public class SubmitOrder implements IDomainService {

    @Resource
    private SubmitOrderStepsExec submitOrderStepsExec;

    public void submit(@NotNull OrderMain orderModel) throws OrderException {
        // 先通过防并发扩展点防止一个订单多次处理：但防并发逻辑在不同场景下不同
        // 同时，也希望研发清楚：扩展点不是绑定到领域步骤的，它可以在任何地方使用！
        Lock lock = DDD.findAbility(SerializableIsolationAbility.class).acquireLock(orderModel);
        if (!SerializableIsolationAbility.useLock(lock)) {
            log.info("will not use lock");
        } else if (!lock.tryLock()) {
            // 存在并发
            throw new OrderException(OrderErrorReason.SubmitOrder.OrderConcurrentNotAllowed);
        }

        // 不同场景下，接单的执行步骤不同：通过扩展点实现业务的多态
        List<String> steps = DDD.findAbility(DecideStepsAbility.class).decideSteps(orderModel, Steps.SubmitOrder.Activity);
        log.info("steps {}", steps);

        if (steps != null && !steps.isEmpty()) {
            // 通过步骤编排的模板方法执行每一个步骤，其中涉及到：步骤回滚，步骤重新编排
            submitOrderStepsExec.execute(Steps.SubmitOrder.Activity, steps, orderModel);
        }

        DDD.findAbility(PostPersistAbility.class).afterPersist(orderModel);

        log.info("接单完毕！");
    }
}
