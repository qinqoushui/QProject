package net.syzc.modules.pm;

import com.jeesite.common.io.FileUtils;
import com.jeesite.common.tests.BaseSpringContextTests;
import net.syzc.modules.Application;
import net.syzc.modules.pm.service.PmModuleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
//@Rollback(false) // 单元测试会默认回滚
public class PmModuleTest extends BaseSpringContextTests {

    @Autowired
    private PmModuleService pmModuleService;

    @Test
    public void testSave4LeadIn() {
        String json= FileUtils.readFileToString("test/module.json");
        pmModuleService.parseJson(json);
       // System.out.println(json);
    }
}