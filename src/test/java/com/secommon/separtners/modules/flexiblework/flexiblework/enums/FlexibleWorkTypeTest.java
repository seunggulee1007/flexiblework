package com.secommon.separtners.modules.flexiblework.flexiblework.enums;

import com.secommon.separtners.modules.flexiblework.flexiblework.FlexibleWorkService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlexibleWorkTypeTest {

    @Test
    void test() {
        FlexibleWorkType week_52 = FlexibleWorkType.valueOf( "WEEK_52" );

        assertEquals( week_52, FlexibleWorkType.WEEK_52 );

    }
}