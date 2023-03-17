package org.example.cp.oms.pattern.extension.coldchain_b2b;

import io.github.dddplus.annotation.Extension;
import org.example.cp.oms.pattern.ColdChainB2BPattern;
import org.example.cp.oms.spec.ext.ISerializableIsolationExt;
import org.example.cp.oms.spec.model.IOrderMain;
import org.example.cp.oms.spec.model.vo.LockEntry;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@Extension(code = ColdChainB2BPattern.CODE, value = "ccb2bSerializableIsolationExt", name = "冷链B2B模式下的防并发机制")
public class SerializableIsolationExt implements ISerializableIsolationExt {

    @Override
    public LockEntry createLockEntry(@NotNull IOrderMain model) {
        return new LockEntry(model.customerProvidedOrderNo(), 19, TimeUnit.MINUTES);
    }
}
