package wx.utils;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 测试类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Test extends BaseJsonBean {
    private static final long serialVersionUID = 5162169325014090941L;

    private String name;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
