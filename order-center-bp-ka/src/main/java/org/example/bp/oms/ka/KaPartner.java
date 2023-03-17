package org.example.bp.oms.ka;

import org.example.cp.oms.spec.model.IOrderMain;
import io.github.dddplus.annotation.Partner;
import io.github.dddplus.ext.IIdentityResolver;

@Partner(code = KaPartner.CODE, name = "KA业务前台")
public class KaPartner implements IIdentityResolver<IOrderMain> {
    public static final String CODE = "KA";

    @Override
    public boolean match(IOrderMain model) {
        if (model.getSource() == null) {
            return false;
        }

        return model.getSource().equalsIgnoreCase(CODE);
    }
}
