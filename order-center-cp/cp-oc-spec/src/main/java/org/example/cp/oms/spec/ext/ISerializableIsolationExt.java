package org.example.cp.oms.spec.ext;

import org.example.cp.oms.spec.model.IOrderMain;
import org.example.cp.oms.spec.model.vo.LockEntry;
import io.github.dddplus.ext.IDomainExtension;

import javax.validation.constraints.NotNull;

/**
 * 订单串行化隔离的扩展点声明，即防并发锁.
 */
public interface ISerializableIsolationExt extends IDomainExtension {

    /**
     * 获取防并发锁的信息.
     *
     * @param model
     * @return lock entry object. if null, 不需要防并发
     */
    LockEntry createLockEntry(@NotNull IOrderMain model);

}
