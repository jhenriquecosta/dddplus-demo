package org.example.cp.oms.pattern.extension.home_appliance;

import lombok.extern.slf4j.Slf4j;
import io.github.dddplus.annotation.Extension;
import org.example.cp.oms.pattern.HomeAppliancePattern;
import org.example.cp.oms.spec.ext.IPresortExt;
import org.example.cp.oms.spec.model.IOrderMain;

import javax.validation.constraints.NotNull;

@Slf4j
@Extension(code = HomeAppliancePattern.CODE, value = "homePresort")
public class PresortExt implements IPresortExt {

    @Override
    public void presort(@NotNull IOrderMain model) {
        log.info("家电的预分拣执行了");
    }
}
