package org.example.cp.oms.spec.ext;

import io.github.dddplus.ext.IDomainExtension;
import org.example.cp.oms.spec.model.IOrderMain;

import javax.validation.constraints.NotNull;

/**
 * 敏感词信息获取.
 */
public interface ISensitiveWordsExt extends IDomainExtension {

    String[] extract(@NotNull IOrderMain model);
}
